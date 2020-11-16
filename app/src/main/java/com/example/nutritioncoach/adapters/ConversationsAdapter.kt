package com.example.nutritioncoach.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nutritioncoach.R
import com.example.nutritioncoach.databinding.LayoutConversationBinding
import com.example.nutritioncoach.model.Conversation

class ConversationsAdapter(val conversations:ArrayList<Conversation>?) : RecyclerView.Adapter<ConversationsAdapter.ConversationHolder>() {

     lateinit var onConversationCLickListener: OnConversationCLickListener

    constructor(onConversationCLickListener: OnConversationCLickListener, conversations:ArrayList<Conversation>?) : this(conversations) {
        this.onConversationCLickListener=onConversationCLickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationHolder {
        val binding= DataBindingUtil.inflate<LayoutConversationBinding>(
            LayoutInflater.from(parent.context),
            R.layout.layout_conversation,parent,false)
        return ConversationHolder(binding)
    }

    override fun getItemCount(): Int {
        if (conversations==null)return 0
         return conversations.size
    }

    override fun onBindViewHolder(holder: ConversationHolder, position: Int) {
        holder.binding.user=conversations?.get(position)?.user

        holder.binding.root.setOnClickListener {
            if (conversations != null) {
                onConversationCLickListener.onConversationCLickListener(conversations.get(position))
            }
        }
    }

    fun addConversations(conversations: ArrayList<Conversation>?){
        if (conversations==null)return
        this.conversations?.addAll(conversations)
        notifyDataSetChanged()
    }

    class ConversationHolder(var binding: LayoutConversationBinding) :RecyclerView.ViewHolder(binding.root){

    }

    interface OnConversationCLickListener{
        fun onConversationCLickListener(conversation: Conversation)
    }
}