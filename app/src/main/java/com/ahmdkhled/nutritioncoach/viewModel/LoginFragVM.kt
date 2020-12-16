package com.ahmdkhled.nutritioncoach.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ahmdkhled.nutritioncoach.model.AuthResult
import com.ahmdkhled.nutritioncoach.repo.AuthRepo

class LoginFragVM(application: Application) :AndroidViewModel(application) {


    suspend fun login(email:String,password:String): AuthResult {
        return AuthRepo().login(email, password)
    }
}