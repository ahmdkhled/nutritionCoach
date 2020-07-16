package com.example.nutritioncoach.repo

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
}