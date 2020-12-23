package com.ahmdkhled.nutritioncoach.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.ahmdkhled.nutritioncoach.model.DietResult
import com.ahmdkhled.nutritioncoach.repo.DietRepo

class DashboardFragVM(application: Application) : AndroidViewModel(application) {
     var dietResult:DietResult? = null
    suspend fun getDietPlan(uid:String?):DietResult{
        Log.d("getCurrentPlan", "vm: ")
        if (dietResult==null)
        dietResult=DietRepo().getCurrentPlan(uid)
        return dietResult as DietResult
    }

}