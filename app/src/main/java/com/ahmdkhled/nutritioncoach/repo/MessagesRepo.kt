package com.ahmdkhled.nutritioncoach.repo

import com.ahmdkhled.nutritioncoach.model.DBResult2
import com.ahmdkhled.nutritioncoach.model.Message
import com.google.firebase.auth.FirebaseAuth
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
                .limit(50)
                .orderBy("timestamp")
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

    suspend fun sendMessage(
        body:String,
        receiverId:String?,
        conversationId: String?,
        senderId:String?=null): Boolean {
        val uid= FirebaseAuth.getInstance().uid ?: return false
        receiverId?:return false
        conversationId?:return false

        var senderUid: String
        if (senderId==null)senderUid=uid
        else senderUid=senderId


        val message=Message(null,body,senderUid,receiverId,System.currentTimeMillis())

        try {
            db.collection("chats")
                .document(conversationId)
                .collection("messages")
                .document()
                .set(message)
                .await()

            return true

        }catch (ex :Exception){
            return false
        }
    }
}