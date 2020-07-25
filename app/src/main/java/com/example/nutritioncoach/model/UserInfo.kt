package com.example.nutritioncoach.model

data class UserInfo(var uid:String?,var name:String?,var age:Int?,var goal:String?,var weight:Int?,var height:Int?
                    ,var weightGoal:Int?,var image:String?) {

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