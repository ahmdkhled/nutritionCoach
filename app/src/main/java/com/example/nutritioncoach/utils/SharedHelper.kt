package com.example.nutritioncoach.utils

import android.content.Context
import android.content.SharedPreferences


object SharedHelper {

    lateinit var sharedPreferences:SharedPreferences
    lateinit var editor:SharedPreferences.Editor

    fun saveToken(context: Context,token:String){
        editor= getSharedPreferences(context).edit()

        editor.putString("token",token).apply()
    }


    fun getToken(context: Context): String? {
        return getSharedPreferences(context).getString("token",null)
    }

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("shared",Context.MODE_PRIVATE)
    }
}