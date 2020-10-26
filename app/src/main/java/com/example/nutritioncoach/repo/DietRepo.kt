package com.example.nutritioncoach.repo

import android.util.Log
import com.example.nutritioncoach.model.Day
import com.example.nutritioncoach.model.DietResult
import com.example.nutritioncoach.model.Plan
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.tasks.await

class DietRepo{
    private  val TAG = "DietRepo"
    val db= FirebaseFirestore.getInstance()

     suspend fun getCurrentPlan(uid: String?): DietResult {
        if (uid==null)return DietResult(null,false,"you are not logged in")
         Log.d(TAG, "getCurrentPlan: "+uid)
         try {

            val userPlan = db.collection("user-diets")
                .document(uid)
                .collection("p")
                .orderBy("date")
                .limit(1)
                .get()
                .await()

             val planId=userPlan.documents.get(0).id;
             val planObj=db.collection("diets")
                 .document(planId)
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

}