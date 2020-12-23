package com.ahmdkhled.nutritioncoach.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ahmdkhled.nutritioncoach.model.AuthResult
import com.ahmdkhled.nutritioncoach.model.Response
import com.ahmdkhled.nutritioncoach.repo.AuthRepo
import com.ahmdkhled.nutritioncoach.repo.ConversationsRepo
import com.ahmdkhled.nutritioncoach.repo.DietRepo
import com.ahmdkhled.nutritioncoach.repo.MessagesRepo

class RegisterVM(application: Application) : AndroidViewModel(application) {


     suspend fun register(email: String, password: String): AuthResult {
        val authRepo =AuthRepo();
        return authRepo.register(email,password);
    }

    public suspend fun addFirstConversation(): Response<String> {
        return ConversationsRepo.addFirstConversation()
    }

    suspend fun sendMessage(
        body: String,
        receiverId: String?,
        conversationId: String?,
        senderId: String?=null
    ): Boolean {
        return MessagesRepo().sendMessage(body,receiverId,conversationId,senderId)
    }

    suspend fun assignPlan(){
        return DietRepo().assignPlan()
    }



}