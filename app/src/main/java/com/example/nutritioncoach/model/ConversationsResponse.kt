package com.example.nutritioncoach.model

import com.google.firebase.firestore.DocumentSnapshot

data class ConversationsResponse(var conversations: ArrayList<Conversation>?, var isSuccessfull :Boolean?, var errorMessage :String?) {
}