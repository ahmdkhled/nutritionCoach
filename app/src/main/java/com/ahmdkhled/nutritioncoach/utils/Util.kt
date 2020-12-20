package com.ahmdkhled.nutritioncoach.utils

class Util {

    companion object{
        fun trimList(list: ArrayList<String>?): ArrayList<String>? {
            if (list==null)return null
            val newList=ArrayList<String>()

            for (item in list){
                newList.add(item.trim())
            }
        return newList
        }

    }
}