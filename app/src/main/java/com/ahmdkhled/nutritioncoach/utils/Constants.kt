package com.ahmdkhled.nutritioncoach.utils

import android.content.Context
import com.ahmdkhled.nutritioncoach.R

class Constants {


    companion object {
        fun getWeekDays(context: Context): Array<String> {
            return context.resources.getStringArray(R.array.weekDays)
        }

    }
}