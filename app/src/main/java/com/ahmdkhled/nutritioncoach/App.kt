package com.ahmdkhled.nutritioncoach

import android.content.Context
import androidx.multidex.MultiDexApplication

class App : MultiDexApplication() {

    companion object{

        lateinit var context: Context;
    }

    override fun onCreate() {
        super.onCreate()
        context=applicationContext

    }

    override fun getBaseContext(): Context {
        return context
    }
}