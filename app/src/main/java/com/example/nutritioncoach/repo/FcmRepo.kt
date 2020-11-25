package com.example.nutritioncoach.repo

import android.util.Log
import com.example.nutritioncoach.model.FcmPayload
import com.example.nutritioncoach.model.FcmRes
import com.example.nutritioncoach.model.Notification
import com.example.nutritioncoach.network.RetrofitClient

class FcmRepo {

    private  val TAG = "FcmRepo"
     suspend fun send(name:String, message :String, token:String): FcmRes {
        val notification=Notification(name,message,"")
        val fcmPayload =FcmPayload(notification,token)
        try {
            return RetrofitClient.getApiService()
                .send(fcmPayload)
        }catch (ex:Exception){
            Log.d(TAG, "send: "+ex.message)
        }
        return FcmRes(0,1)



    }
}