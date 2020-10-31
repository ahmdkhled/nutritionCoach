package com.example.nutritioncoach.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.nutritioncoach.model.AuthResult
import com.example.nutritioncoach.repo.AuthRepo

class LoginFragVM(application: Application) :AndroidViewModel(application) {


    suspend fun login(email:String,password:String): AuthResult {
        return AuthRepo().login(email, password)
    }
}