package com.ahmdkhled.nutritioncoach.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfo(
    var uid:String?=null,
    var name:String?=null,
    var age:Int?=null,
    var goal:String?=null,
    var weight:Int?=null,
    var height:Int?=null,
    var weightGoal:Int?=null,
    var image:String?=null,
    var token:String?=null,
    var level:Int?=3

) :Parcelable{




}