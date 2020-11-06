package com.example.nutritioncoach.view

import android.os.Bundle
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
import com.example.nutritioncoach.model.Message
import com.example.nutritioncoach.repo.MessagesRepo
import com.example.nutritioncoach.viewModel.ChatFragVM
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatFragment : Fragment() {

    lateinit var adapter:MessagesAdapter
    lateinit var chatFragVM:ChatFragVM
    private  val TAG = "ChatFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding=DataBindingUtil.inflate<FragmentChatBinding>(inflater,R.layout.fragment_chat,container,false)
        chatFragVM=ViewModelProvider(this).get(ChatFragVM::class.java)

        adapter=MessagesAdapter(getFakeMessages())
        binding.chatRecycler.adapter=adapter
        val layoutManager =LinearLayoutManager(context)
        binding.chatRecycler.layoutManager=layoutManager
        layoutManager.stackFromEnd=true

        binding.send.setOnClickListener{
            val message=binding.messageBox.text.toString()

        }

        GlobalScope.launch {
            val documents= chatFragVM.getMessages("HFJSBFJFIJIEJRI")
                .documentSnapshot?.documents

            if (documents != null) {
                for (doc in documents){
                    val message: Message? =doc.toObject(Message::class.java)
                    if (message != null) { message.id=doc.id }
                    Log.d(TAG, "onCreateView: "+message.toString())
                    withContext(Dispatchers.Main){
                        adapter.addMessage(message!!)
                    }
                }
            }
        }

        return binding.root
    }

    fun getFakeMessages():ArrayList<Message>
    {
        val messages=ArrayList<Message>()
        messages.add(Message("1","Hello Dococtor",1,"32"))
        messages.add(Message("2","Hello",2,"32"))
        messages.add(Message("3","i need some advice with my diet plan",1,"32"))
        messages.add(Message("4","can i increase potein in my plan",1,"32"))

        return messages
    }


}