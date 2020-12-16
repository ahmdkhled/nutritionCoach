package com.ahmdkhled.nutritioncoach.repo

import android.util.Log
import com.ahmdkhled.nutritioncoach.model.DBResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepo {
    private  val TAG = "UserRepo"
    var db= FirebaseFirestore.getInstance();
    val uid=FirebaseAuth.getInstance().uid

    public suspend fun saveUserData(uid :String,name :String,age:Int ,goal : String,height :Int ,weight :Int):Boolean{
        val user=HashMap<String,Any>()
        user["name"] = name
        user["age"] = age
        user["goal"] = goal
        user["height"] = height
        user["weight"] = weight

        try {

            db.collection("users")
                .document(uid)
                .set(user)
                .await();
            return true;
        }catch (exception :Exception){
            Log.d(TAG, "saveUserData: "+exception)
            return false;
        }

    }

    suspend fun getUserData(uid:String): DBResult? {
        try {
        val result= db.collection("users")
            .document(uid)
            .get()
            .await()
            return DBResult(result,true,null)

        }catch (e:Exception){
            return DBResult(null,false,e.message)

        }
    }

    fun updateToken(newToken :String){
        if (uid==null)return
        db.collection("users")
            .document(uid)
            .update("token",newToken)
            .addOnCompleteListener { 
                if (it.isSuccessful)
                    Log.d(TAG, "updateToken: ")
                else
                    Log.d(TAG, "updateToken: failed"+it.exception)
            }


    }
}