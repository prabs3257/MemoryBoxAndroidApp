package com.example.memorybox.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    fun getInstance(): Retrofit{

        return Retrofit.Builder()
            .baseUrl("http://192.168.29.189:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}