package com.example.nutritioncoach.repo

import android.content.Context
import android.widget.Toast
import com.example.nutritioncoach.model.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthRepo{
    private var auth:FirebaseAuth = Firebase.auth;

    suspend fun register(email :String ,password :String) : AuthResult{
        try {


            val result= auth.createUserWithEmailAndPassword(email, password)
                .await();
            return AuthResult(result,true,null)
        }catch (exception : Exception){

            return AuthResult(null,false,exception.message);
        }
    }
}