package com.example.nutritioncoach.model

data class Message(
    var id: String? ="",
    var body:String="",
    var state:Int=0,
    var senderId:String="",
    var receiverId:String="",
    var timestamp:Long=0) {
}