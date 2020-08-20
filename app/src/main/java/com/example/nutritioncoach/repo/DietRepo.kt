package com.example.nutritioncoach.repo

import android.util.Log
import com.example.nutritioncoach.model.DietResult
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class DietRepo{
    private  val TAG = "DietRepo"
    val db= FirebaseFirestore.getInstance()

     suspend fun getCurrentPlan(uid: String?): DietResult? {
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
             val plan=db.collection("diets")
                 .document(planId)
                 .get()
                 .await();





             Log.d(TAG, "getCurrentPlan: "+plan.data)

            //return DietResult(result,true,null)
            return DietResult(plan.data,false,null)
        }catch (exception:Exception){
            Log.d(TAG, "getCurrentPlan: "+exception)
            return DietResult(null,false,exception.message)
        }

     }

}