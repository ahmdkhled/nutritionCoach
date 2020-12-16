package com.ahmdkhled.nutritioncoach.network

import com.google.gson.Gson

class GsonHelper {

    fun toString(T:Any) :String{
        return Gson().toJson(T)
    }
}