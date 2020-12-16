package com.ahmdkhled.nutritioncoach.model

import android.os.Parcelable
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Conversation (var id:String?=null,var users:ArrayList<String>?=null,var user:UserInfo?=null) : Parcelable {

    fun getOtherUid(): String? {
        val uid=FirebaseAuth.getInstance().uid
        if (uid != null) {
            users?.remove(uid)
            return users?.get(0)
        }
        return null
    }
}