package com.example.nutritioncoach.view

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nutritioncoach.R
import com.example.nutritioncoach.adapters.MessagesAdapter
import com.example.nutritioncoach.databinding.FragmentChatBinding
import com.example.nutritioncoach.model.Conversation
import com.example.nutritioncoach.model.Message
import com.example.nutritioncoach.viewModel.ChatFragVM
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class ChatFragment : Fragment() {

    val CONVERSATION= "conversation"
    lateinit var binding:FragmentChatBinding
    lateinit var adapter:MessagesAdapter
    lateinit var chatFragVM:ChatFragVM
    var conversation: Conversation?=null
    private  val TAG = "ChatFragment"
    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_chat,container,false)
        val b=arguments

        conversation=b?.getParcelable(CONVERSATION)
        Log.d(TAG, "onCreateView: "+conversation)


        chatFragVM=ViewModelProvider(this).get(ChatFragVM::class.java)

        adapter=MessagesAdapter(ArrayList())
        binding.chatRecycler.adapter=adapter
        val layoutManager =LinearLayoutManager(context)
        binding.chatRecycler.layoutManager=layoutManager
        layoutManager.stackFromEnd=true

        binding.send.setOnClickListener{
            val message=binding.messageBox.text.toString()
            if (TextUtils.isEmpty(message)){
                context?.let { ctx -> Toasty.error(ctx,"please type a message",Toasty.LENGTH_SHORT).show() }
                return@setOnClickListener
            }
            sendMessage(message)

        }

        getMessages()

        return binding.root
    }

    @ExperimentalCoroutinesApi
    fun getMessages(){

        GlobalScope.launch {
            val conversationId=conversation?.id
            if (conversationId==null)return@launch
            val res=chatFragVM.getMessages(conversationId);

            res.collect{

                if (!it.isSuccessfull){
                    context?.let { Toasty.error(it,"Error Loading Chat",Toasty.LENGTH_LONG).show()
                        return@collect
                    }
                }

                val documents= it.documentSnapshot?.documents

                if (documents != null&&documents.size>0) {
                    val messages=ArrayList<Message>()
                    for (doc in documents){
                        val message: Message? =doc.toObject(Message::class.java)
                        if (message != null) {
                            message.id=doc.id
                            messages.add(message)

                        }
                        Log.d(TAG, "onCreateView: "+message.toString())

                    }
                    withContext(Dispatchers.Main){
                        Log.d(TAG, "getMessages: "+messages.toString())
                        adapter.setChatMessages(messages)
                    }
                }
            }



        }
    }

    fun getFakeMessages():ArrayList<Message>
    {
        val messages=ArrayList<Message>()
        messages.add(Message("1","Hello Dococtor","32"))
        messages.add(Message("2","Hello","32"))
        messages.add(Message("3","i need some advice with my diet plan","32"))
        messages.add(Message("4","can i increase potein in my plan","32"))

        return messages
    }

    fun sendMessage(message:String){
        binding.messageBox.setText("")
        GlobalScope.launch {
            val success=chatFragVM.sendMessage(message,conversation?.getOtherUid())
            if (!success){
                withContext(Dispatchers.Main) {
                    context?.let { ctx ->
                        Toasty.error(
                            ctx,
                            "error sending message",
                            Toasty.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }

    }

}