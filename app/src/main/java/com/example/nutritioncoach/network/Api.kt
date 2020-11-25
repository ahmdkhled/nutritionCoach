package com.example.nutritioncoach.network

import com.example.nutritioncoach.model.FcmPayload
import com.example.nutritioncoach.model.FcmRes
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface Api {

    @Headers("Content-Type: application/json","Authorization:key=AAAA-hA1n6s:APA91bH3GHkbBI-YQJNrGHr8dayQkQanozuns1QUchklmtaAfcW2BTyqmJAibkByCw6TqjSQBF7CeM9dapYTv5-7rThAfruenlnViLHGcPrtqg0qeG9QPKvHrrmV9rK21IRR1JukkkJj")
    @POST("send")
    suspend fun send(@Body payload: FcmPayload):FcmRes
}