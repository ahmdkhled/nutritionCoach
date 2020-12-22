package com.ahmdkhled.nutritioncoach.model

data class Response<T>(
    var  model:T?,
    var loading:Boolean,
    var success:Boolean,
    var error:String?
) {
}