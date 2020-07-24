package com.example.nutritioncoach.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.nutritioncoach.model.DBResult
import com.example.nutritioncoach.repo.UserRepo

class ProfileFragVM(application: Application) : AndroidViewModel(application) {

    suspend fun getUserInfo(uid:String):DBResult?{
        return UserRepo().getUserData(uid);
    }
}