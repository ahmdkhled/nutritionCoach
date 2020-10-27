package com.example.nutritioncoach.utils

import android.content.Context
import com.example.nutritioncoach.R

class Constants {


    companion object {
        fun getWeekDays(context: Context): Array<String> {
            return context.resources.getStringArray(R.array.weekDays)
        }

    }
}