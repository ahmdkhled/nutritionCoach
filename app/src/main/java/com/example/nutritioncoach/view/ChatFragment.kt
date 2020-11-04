package com.example.nutritioncoach.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nutritioncoach.R
import com.example.nutritioncoach.adapters.MessagesAdapter
import com.example.nutritioncoach.databinding.FragmentChatBinding
import com.example.nutritioncoach.model.Message

class ChatFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding=DataBindingUtil.inflate<FragmentChatBinding>(inflater,R.layout.fragment_chat,container,false)

        binding.chatRecycler.adapter=MessagesAdapter(getFakeMessages())
        val layoutManager =LinearLayoutManager(context)
        binding.chatRecycler.layoutManager=layoutManager
        layoutManager.stackFromEnd=true

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