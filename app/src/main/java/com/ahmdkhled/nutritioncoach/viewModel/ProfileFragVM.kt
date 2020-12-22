package com.ahmdkhled.nutritioncoach.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ahmdkhled.nutritioncoach.model.DBResult
import com.ahmdkhled.nutritioncoach.repo.UserRepo

class ProfileFragVM(application: Application) : AndroidViewModel(application) {

    suspend fun getUserInfo(uid:String):DBResult?{
        return UserRepo().getUserData(uid);
    }
    public suspend fun updateUserData(field:String,value:Any):Boolean{
        return UserRepo().updateUserData(field,value)
    }

    }