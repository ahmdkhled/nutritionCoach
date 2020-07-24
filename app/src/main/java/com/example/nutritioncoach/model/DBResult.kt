package com.example.nutritioncoach.model

import com.google.firebase.firestore.DocumentSnapshot


data class DBResult(var documentSnapshot: DocumentSnapshot?, var isSuccessfull :Boolean?, var errorMessage :String?) {
}