package com.example.nutritioncoach.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfo(var uid:String?=null,var name:String?=null,var age:Int?=null,var goal:String?=null,var weight:Int?=null
                    ,var height:Int?=null,var weightGoal:Int?=null,var image:String?=null) :Parcelable{

    constructor(map:MutableMap<String,Any>) : this(null,null,null,null,null
        ,null,null,null) {
        uid=map["uid"].toString()
        name=map["name"].toString()
        goal=map["goal"].toString()
        image=map["image"].toString()

        try {
            age= map["age"].toString().trim().toInt()

        }catch (ex :Exception){
            age=-1
        }
        try {
            weight= map["weight"].toString().trim().toInt()

        }catch (ex :Exception){
            weight=-1
        }
        try {
            height= map["height"].toString().trim().toInt()

        }catch (ex :Exception){
            height=-1
        }
        try {
            weightGoal= map["weightGoal"].toString().trim().toInt()

        }catch (ex :Exception){
         weightGoal=-1
        }
    }

     var bmi:String = "0";







}