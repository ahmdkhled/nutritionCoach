package com.example.nutritioncoach.model

import com.google.firebase.firestore.QuerySnapshot


data class DBResult2(var documentSnapshot: QuerySnapshot?, var isSuccessfull:Boolean?, var errorMessage:String?) {
}