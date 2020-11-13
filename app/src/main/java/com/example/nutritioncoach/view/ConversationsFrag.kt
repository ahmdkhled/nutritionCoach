package com.example.nutritioncoach.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nutritioncoach.R
import com.example.nutritioncoach.adapters.ConversationsAdapter
import com.example.nutritioncoach.databinding.FragmentConversationsBinding
import com.example.nutritioncoach.repo.ConversationsRepo
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class ConversationsFrag : Fragment(){

    lateinit var binding:FragmentConversationsBinding
    lateinit var adapter:ConversationsAdapter
    private  val TAG = "ConversationsFrag"

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_conversations,container,false)
        binding.conversationRecycler.layoutManager=LinearLayoutManager(context)
        adapter= ConversationsAdapter(ArrayList())
        binding.conversationRecycler.adapter=adapter
        getConversations()
        return binding.root
    }

    @ExperimentalCoroutinesApi
    fun getConversations(){
        GlobalScope.launch {
            ConversationsRepo().getConversations()
                .collect {
                    Log.d(TAG, "onCreateView: "+ (it.conversations?: "error"))
                    binding.progressBar.visibility=View.GONE
                    if (it.isSuccessfull){
                        withContext(Dispatchers.Main){
                            adapter.addConversations(it.conversations)
                        }
                    }


                }

        }
    }

}