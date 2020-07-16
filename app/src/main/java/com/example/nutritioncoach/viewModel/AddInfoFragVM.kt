package com.example.nutritioncoach.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.nutritioncoach.repo.UserRepo

class AddInfoFragVM(application: Application) : AndroidViewModel(application) {

    public suspend fun saveUserData(uid :String,name :String,age:Int ,goal : String,height :Int ,weight :Int):Boolean{
        return UserRepo().saveUserData(uid,name,age,goal,height,weight)
    }
}