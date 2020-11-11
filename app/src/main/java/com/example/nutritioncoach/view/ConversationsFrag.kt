package com.example.nutritioncoach.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.nutritioncoach.R
import com.example.nutritioncoach.databinding.FragmentConversationsBinding
import com.example.nutritioncoach.repo.ConversationsRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ConversationsFrag : Fragment(){

    lateinit var binding:FragmentConversationsBinding
    private  val TAG = "ConversationsFrag"

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_conversations,container,false)


        getConversations()
        return binding.root
    }

    @ExperimentalCoroutinesApi
    fun getConversations(){
        GlobalScope.launch {
            ConversationsRepo().getConversations()
                .collect {
                    Log.d(TAG, "onCreateView: "+ (it.conversations?: "error"))

                }

        }
    }

}