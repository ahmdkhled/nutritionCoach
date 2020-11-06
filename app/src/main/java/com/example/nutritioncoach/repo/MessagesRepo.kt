package com.example.nutritioncoach.repo

import com.example.nutritioncoach.model.DBResult2
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MessagesRepo {

    val db=FirebaseFirestore.getInstance()

    suspend fun getMessages(conversationId:String): DBResult2 {
        try {


            val result = db.collection("chats")
                .document(conversationId)
                .collection("messages")
                .limit(10)
                .get()
                .await()
            return DBResult2(result,true,null)
        }catch (ex:Exception){
            return DBResult2(null,false,ex.message)
        }



    }
}