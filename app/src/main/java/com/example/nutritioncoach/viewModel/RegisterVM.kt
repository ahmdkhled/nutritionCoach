package com.example.nutritioncoach.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.nutritioncoach.model.AuthResult
import com.example.nutritioncoach.repo.AuthRepo

class RegisterVM(application: Application) : AndroidViewModel(application) {


     suspend fun register(email: String, password: String): AuthResult {
        val authRepo =AuthRepo();
        return authRepo.register(email,password);
    }


}