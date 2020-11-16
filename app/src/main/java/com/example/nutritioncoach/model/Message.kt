package com.example.nutritioncoach.model

import com.google.firebase.firestore.Exclude

data class Message(
    @get:Exclude
    var id: String? ="",
    var body:String="",
    var senderId:String="",
    var receiverId:String="",
    var timestamp:Long=0) {
}