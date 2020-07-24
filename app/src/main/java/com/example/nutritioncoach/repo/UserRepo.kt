package com.example.nutritioncoach.repo

import com.example.nutritioncoach.model.DBResult
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserRepo {
    var db= FirebaseFirestore.getInstance();

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

}