package com.example.nutritioncoach.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.nutritioncoach.model.DBResult2
import com.example.nutritioncoach.repo.MessagesRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class ChatFragVM(application: Application) : AndroidViewModel(application) {

    @ExperimentalCoroutinesApi
    suspend fun getMessages(conversationId: String): Flow<DBResult2> {
        return MessagesRepo().getMessages(conversationId)
    }

    suspend fun sendMessage(body:String,receiverId:String): Boolean {
        return MessagesRepo().sendMessage(body,receiverId)
    }

    }