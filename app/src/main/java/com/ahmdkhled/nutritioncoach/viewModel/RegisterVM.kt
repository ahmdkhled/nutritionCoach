package com.ahmdkhled.nutritioncoach.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ahmdkhled.nutritioncoach.model.AuthResult
import com.ahmdkhled.nutritioncoach.repo.AuthRepo

class RegisterVM(application: Application) : AndroidViewModel(application) {


     suspend fun register(email: String, password: String): AuthResult {
        val authRepo =AuthRepo();
        return authRepo.register(email,password);
    }


}