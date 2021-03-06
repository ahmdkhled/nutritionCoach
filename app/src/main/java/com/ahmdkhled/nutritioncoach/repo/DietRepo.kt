package com.ahmdkhled.nutritioncoach.repo

import android.util.Log
import com.ahmdkhled.nutritioncoach.model.Day
import com.ahmdkhled.nutritioncoach.model.DietResult
import com.ahmdkhled.nutritioncoach.model.Plan
import com.ahmdkhled.nutritioncoach.model.PlanData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList

class DietRepo{
    private  val TAG = "DietRepo"
    val db= FirebaseFirestore.getInstance()
    val uid=Firebase.auth.uid
    val year=Calendar.getInstance().get(Calendar.YEAR)
    val week=Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)

     suspend fun getCurrentPlan(uid: String?): DietResult {
        if (uid==null)return DietResult(null,false,"you are not logged in")
         Log.d(TAG, "getCurrentPlan: "+uid)
         val weekOfYear=Calendar.getInstance().get(Calendar.WEEK_OF_YEAR).toString()
         val year=Calendar.getInstance().get(Calendar.YEAR).toString()
         val currentPlanRef=(weekOfYear+year)

         try {

            val userPlan = db.collection("user-diets")
                .document(uid)
                .collection("p")
                .document(currentPlanRef)
                .get()
                .await()

             Log.d(TAG, "getCurrentPlan: $currentPlanRef")
             Log.d(TAG, "getCurrentPlan: ${userPlan.data}")
             val planData=userPlan.toObject(PlanData::class.java)
             if (planData==null||planData.plan==null){
                 return DietResult(null,false,"there is no plan")

             }
             val planObj=db.collection("diets")
                 .document(planData.plan!!)
                 .get()
                 .await();

             val json= planObj.data?.get("diet").toString();

             val gson=Gson()
             val itemType = object : TypeToken<ArrayList<Day>>() {}.type
             val planDays=gson.fromJson<ArrayList<Day>>(json,itemType)

             val plan= Plan(planDays)




             Log.d(TAG, "getCurrentPlan: "+ planDays.size)

            //return DietResult(result,true,null)
            return DietResult(plan,true,null)
        }catch (exception:Exception){
            Log.d(TAG, "getCurrentPlan: "+exception)
            return DietResult(null,false,exception.message)
        }

     }

    suspend fun assignPlan(planWeek:String,plan :String){
        if (uid==null)return
        db.collection("user-diets")
            .document(uid)
            .collection("p")
            .document(planWeek)
            .set(PlanData(plan,System.currentTimeMillis()))
            .await()
    }

    suspend fun assignPlan(){
        val week1=week.toString()+year.toString()
        val week2=(week+1).toString()+year.toString()
        val week3=(week+2).toString()+year.toString()
        assignPlan(week1,"1")
        assignPlan(week2,"1")
        assignPlan(week3,"1")
    }

}