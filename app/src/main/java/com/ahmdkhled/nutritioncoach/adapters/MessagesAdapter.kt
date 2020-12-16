package com.ahmdkhled.nutritioncoach.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahmdkhled.nutritioncoach.R
import com.ahmdkhled.nutritioncoach.databinding.LayoutIncomingChatBinding
import com.ahmdkhled.nutritioncoach.databinding.LayoutOutgoingChatBinding
import com.ahmdkhled.nutritioncoach.model.Message
import com.google.firebase.auth.FirebaseAuth

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
        if (messages.get(position).senderId==FirebaseAuth.getInstance().uid)
            return OUTGOING_MESSAGE_TYPE
        else
            return INCOMING_MESSAGE_TYPE


    }

    fun addMessage(message:Message){
        messages.add(message)
        notifyDataSetChanged()
    }
    fun setChatMessages(messages: ArrayList<Message>){
        this.messages.clear()
        this.messages=messages
        notifyDataSetChanged()
    }

    fun clear(){
        messages.clear()
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