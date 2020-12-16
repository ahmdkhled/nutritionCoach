package com.ahmdkhled.nutritioncoach.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ahmdkhled.nutritioncoach.model.DBResult2
import com.ahmdkhled.nutritioncoach.model.FcmRes
import com.ahmdkhled.nutritioncoach.repo.FcmRepo
import com.ahmdkhled.nutritioncoach.repo.MessagesRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class ChatFragVM(application: Application) : AndroidViewModel(application) {

    @ExperimentalCoroutinesApi
    suspend fun getMessages(conversationId: String): Flow<DBResult2> {
        return MessagesRepo().getMessages(conversationId)
    }

    suspend fun sendMessage(body:String,receiverId:String?): Boolean {
        return MessagesRepo().sendMessage(body,receiverId)
    }

//    suspend fun sendNotification(name:String, message :String, token:String): FcmRes {
//        return FcmRepo().send(name,message,token)
//    }

    }