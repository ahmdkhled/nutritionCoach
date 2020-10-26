package com.example.nutritioncoach.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.nutritioncoach.model.DietResult
import com.example.nutritioncoach.model.Plan
import com.example.nutritioncoach.repo.DietRepo

class DashboardFragVM(application: Application) : AndroidViewModel(application) {
     var dietResult:DietResult? = null
    suspend fun getDietPlan(uid:String?):DietResult{
        if (dietResult==null)
        dietResult=DietRepo().getCurrentPlan(uid)
        return dietResult as DietResult
    }

}