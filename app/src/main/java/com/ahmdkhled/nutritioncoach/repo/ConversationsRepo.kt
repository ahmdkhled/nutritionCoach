package com.ahmdkhled.nutritioncoach.repo

import android.util.Log
import com.ahmdkhled.nutritioncoach.model.Conversation
import com.ahmdkhled.nutritioncoach.model.ConversationsResponse
import com.ahmdkhled.nutritioncoach.model.UserInfo
import com.ahmdkhled.nutritioncoach.utils.Util
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ConversationsRepo {

    val db=FirebaseFirestore.getInstance()
    val uid=FirebaseAuth.getInstance().uid
    private  val TAG = "ConversationsRepo"

     @ExperimentalCoroutinesApi
     fun getConversations(): Flow<ConversationsResponse> {
        if (uid==null)return callbackFlow {
            sendBlocking(ConversationsResponse(null,false,null))
        }
         return callbackFlow<ConversationsResponse> {
             db.collection("chats")
                 .whereArrayContains("users",uid)
                 .get(Source.SERVER).addOnCompleteListener{
                     if (it.isSuccessful){
                         Log.d(TAG, "getConversations: ${it.result?.documents}")
                         val res= it.result
                         if (res!=null){
                             val conversations=ArrayList<Conversation>()
                             var count=0;
                             for (doc in res.documents){
                                 count++
                                 val conversation=doc.toObject(Conversation::class.java)
                                 conversation?.id=doc.id
                                 Log.d(TAG, "getConversations: ${conversation?.users}")
                                 val users=Util.trimList(conversation?.users)

                                 uid.let { uid -> users?.remove(uid.trim() ) }
                                 val userId =users?.get(0).toString().trim()
                                 Log.d(TAG, "user id :"+userId)
                                 Log.d(TAG, "getConversations2: ${conversation?.users}")

                                 db.collection("users").document(userId)
                                         .get()
                                         .addOnCompleteListener{
                                             Log.d(TAG, "getConversations: "+it.result?.toObject(UserInfo::class.java))
                                             if (it.isSuccessful){
                                                 val user=it.result?.toObject(UserInfo::class.java)
                                                 user?.uid=userId
                                                 conversation?.user= user
                                                 conversation?.let { it1 -> conversations.add(it1) }
                                                 if (count==res.documents.size)
                                                     sendBlocking(ConversationsResponse(conversations,true,null))
                                             }

                                         }

                             }
                             Log.d(TAG, "final result: "+conversations)

                         }
                     }
                 };

             awaitClose {

             }
         }


    }
}