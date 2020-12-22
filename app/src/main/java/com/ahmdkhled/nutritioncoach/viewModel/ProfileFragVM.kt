package com.ahmdkhled.nutritioncoach.viewModel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ahmdkhled.nutritioncoach.model.DBResult
import com.ahmdkhled.nutritioncoach.model.Response
import com.ahmdkhled.nutritioncoach.repo.UserRepo
import com.google.firebase.storage.UploadTask

class ProfileFragVM(application: Application) : AndroidViewModel(application) {

    suspend fun getUserInfo(uid:String):DBResult?{
        return UserRepo().getUserData(uid);
    }
    public suspend fun updateUserData(field:String,value:Any):Boolean{
        return UserRepo().updateUserData(field,value)
    }

    suspend fun uploadProfileImage(uri: Uri): MutableLiveData<Response<Uri>> {
        return UserRepo().uploadProfileImage(uri)
    }


    }