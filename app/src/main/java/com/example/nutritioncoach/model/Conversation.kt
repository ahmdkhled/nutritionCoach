package com.example.nutritioncoach.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Conversation (var id:String?=null,var users:ArrayList<String>?=null,var user:UserInfo?=null) : Parcelable {

}