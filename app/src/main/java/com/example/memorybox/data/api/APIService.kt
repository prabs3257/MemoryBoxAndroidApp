package com.example.memorybox.data.api

import com.example.memorybox.data.models.Memory
import com.example.memorybox.data.models.User

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @POST("user")
    @Headers("Accept:application/json", "Content-Type:application/json")
    suspend fun addUser(@Body user: User)

    @POST("addMemory")
    @Headers("Accept:application/json", "Content-Type:application/json")
    suspend fun addMemory(@Body memory: Memory)

    @GET("getMemories")
    suspend fun getMemories(): Response<List<Memory>>
}