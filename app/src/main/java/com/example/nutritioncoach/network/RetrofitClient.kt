package com.example.nutritioncoach.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {



    private var retrofit=Retrofit.Builder()
        .baseUrl("https://fcm.googleapis.com/fcm/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(getInterceptor())
        .build()

    fun getApiService(): Api {
        return retrofit.create(Api::class.java)
    }

    private fun getInterceptor(): OkHttpClient {
        val logging=  HttpLoggingInterceptor()
        logging.level=HttpLoggingInterceptor.Level.BODY
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(logging)
            .build();
    }
    
}