package com.example.nutritioncoach.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.nutritioncoach.model.DBResult2
import com.example.nutritioncoach.repo.MessagesRepo

class ChatFragVM(application: Application) : AndroidViewModel(application) {

    suspend fun getMessages(conversationId: String): DBResult2 {
        return MessagesRepo().getMessages(conversationId)
    }
}