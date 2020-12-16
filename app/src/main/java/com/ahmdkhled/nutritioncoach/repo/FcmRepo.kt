package com.ahmdkhled.nutritioncoach.repo

import android.util.Log
import com.ahmdkhled.nutritioncoach.model.FcmPayload
import com.ahmdkhled.nutritioncoach.model.FcmRes
import com.ahmdkhled.nutritioncoach.model.Notification
import com.ahmdkhled.nutritioncoach.network.RetrofitClient

class FcmRepo {

    private  val TAG = "FcmRepo"
     suspend fun send(name:String, message :String, token:String): FcmRes {
//        val notification=Notification(name,message,"")
//        val fcmPayload =FcmPayload(notification,token)
//        try {
//            return RetrofitClient.getApiService()
//                .send(fcmPayload)
//        }catch (ex:Exception){
//            Log.d(TAG, "send: "+ex.message)
//        }
        return FcmRes(0,1)



    }
}