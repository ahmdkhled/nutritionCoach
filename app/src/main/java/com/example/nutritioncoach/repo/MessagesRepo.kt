package com.example.nutritioncoach.repo

import com.example.nutritioncoach.model.DBResult2
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class MessagesRepo {

    val db=FirebaseFirestore.getInstance()

    @ExperimentalCoroutinesApi
    suspend fun getMessages(conversationId:String): Flow<DBResult2> {

        val flow= callbackFlow<DBResult2> {
            val result = db.collection("chats")
                .document(conversationId)
                .collection("messages")
                .limit(10)
                .addSnapshotListener { value, error ->
                    val res=DBResult2(value,true,null)
                    this@callbackFlow.sendBlocking(res)
                }
            awaitClose {
                result.remove()
            }

        }


        return flow

    }
}