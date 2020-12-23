package com.ahmdkhled.nutritioncoach.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmdkhled.nutritioncoach.R
import com.ahmdkhled.nutritioncoach.adapters.ConversationsAdapter
import com.ahmdkhled.nutritioncoach.databinding.FragmentConversationsBinding
import com.ahmdkhled.nutritioncoach.model.Conversation
import com.ahmdkhled.nutritioncoach.repo.ConversationsRepo
import com.ahmdkhled.nutritioncoach.viewModel.ConversationFragVM
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class ConversationsFrag : Fragment() ,ConversationsAdapter.OnConversationCLickListener {

    lateinit var binding:FragmentConversationsBinding
    lateinit var conversationFragVM:ConversationFragVM
    lateinit var adapter:ConversationsAdapter
    private  val TAG = "ConversationsFrag"

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_conversations,container,false)
        conversationFragVM=ViewModelProvider(this).get(ConversationFragVM::class.java)
        binding.conversationRecycler.layoutManager=LinearLayoutManager(context)
        adapter= ConversationsAdapter(this,ArrayList())
        binding.conversationRecycler.adapter=adapter
        getConversations()
        return binding.root
    }

    @ExperimentalCoroutinesApi
    fun getConversations(){
        GlobalScope.launch {
            conversationFragVM.getConversations()
                .collect {
                    withContext(Dispatchers.Main){ binding.progressBar.visibility=View.GONE }
                    if (it.isSuccessfull){
                        withContext(Dispatchers.Main){
                            adapter.addConversations(it.conversations)
                        }
                    }


                }

        }
    }

    override fun onConversationCLickListener(conversation: Conversation) {
        Log.d(TAG, "onConversationCLickListener: "+conversation)
        val chatFragment=ChatFragment()
        val b=Bundle()
        b.putParcelable(chatFragment.CONVERSATION,conversation)
        chatFragment.arguments=b
        (activity as MainActivity).addFragment(chatFragment)
    }

}