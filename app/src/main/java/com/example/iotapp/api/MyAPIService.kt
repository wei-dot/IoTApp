package com.example.iotapp.api

import com.example.iotapp.UserInfo
import retrofit2.Call
import retrofit2.http.*

interface MyAPIService {

    @GET("api/user_info/{id}")
    fun getUserInfoById(@Path("id") id: Int): Call<UserInfo>

    @Headers("Content-Type:application/json")
    @POST("api/user_info/")
    fun postUserInfo(@Body info: UserInfo): Call<UserInfo>


}