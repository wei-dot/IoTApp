package com.example.iotapp.api

import retrofit2.Call
import retrofit2.http.*

interface MyAPIService {

    @GET("user_info/{id}")
    fun getUserInfoById(@Path("id") id: Int): Call<UserInfo>

    @Headers("Content-Type:application/json")
    @POST("user_info/")
    fun postUserInfo(@Body info: UserInfo): Call<UserInfo>



}