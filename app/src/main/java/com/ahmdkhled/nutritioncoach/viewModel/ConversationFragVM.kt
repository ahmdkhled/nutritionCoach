package com.ahmdkhled.nutritioncoach.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ahmdkhled.nutritioncoach.model.ConversationsResponse
import com.ahmdkhled.nutritioncoach.repo.ConversationsRepo
import kotlinx.coroutines.flow.Flow

class ConversationFragVM(application: Application) :AndroidViewModel(application) {

    fun getConversations(): Flow<ConversationsResponse> {
        return ConversationsRepo.getConversations()
    }

    }