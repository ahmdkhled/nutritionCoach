package com.example.nutritioncoach.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nutritioncoach.R
import com.example.nutritioncoach.databinding.LayoutIncomingChatBinding
import com.example.nutritioncoach.databinding.LayoutOutgoingChatBinding
import com.example.nutritioncoach.databinding.LayoutOutgoingChatBindingImpl
import com.example.nutritioncoach.model.Message

class MessagesAdapter(var messages:ArrayList<Message>) :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val INCOMING_MESSAGE_TYPE=1;
    val OUTGOING_MESSAGE_TYPE=2;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==INCOMING_MESSAGE_TYPE){
            val binding=DataBindingUtil.inflate<LayoutIncomingChatBinding>(
                LayoutInflater.from(parent.context), R.layout.layout_incoming_chat,parent,false)
            return IncomingMessageHolder(binding)

        }else {
            val binding=DataBindingUtil.inflate<LayoutOutgoingChatBinding>(
                LayoutInflater.from(parent.context), R.layout.layout_outgoing_chat,parent,false)
            return OutgoingMessageHolder(binding)
        }


    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position)==INCOMING_MESSAGE_TYPE){
            val holder=viewHolder as IncomingMessageHolder
            holder.bind(messages.get(position))

        }else if (getItemViewType(position)==OUTGOING_MESSAGE_TYPE){
            val holder=viewHolder as OutgoingMessageHolder
            holder.bind(messages.get(position))
        }

    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        if (messages.get(position).state==INCOMING_MESSAGE_TYPE)
            return INCOMING_MESSAGE_TYPE
        else if (messages.get(position).state==OUTGOING_MESSAGE_TYPE)
            return OUTGOING_MESSAGE_TYPE

        return 3
    }

    fun addMessage(message:Message){
        messages.add(message)
        notifyDataSetChanged()
    }

    class OutgoingMessageHolder(var binding: LayoutOutgoingChatBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(message:Message){
            binding.message.setText(message.body)
        }
    }

    class IncomingMessageHolder(var binding: LayoutIncomingChatBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(message:Message){
            binding.message.setText(message.body)
        }
    }

}