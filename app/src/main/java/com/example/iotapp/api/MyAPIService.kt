package com.example.iotapp.api

import retrofit2.Call
import retrofit2.http.*

interface MyAPIService {

    @Headers("Content-Type: application/json")
    @POST("dj-rest-auth/login/")
    fun Login(@Body info: LoginInfo): Call<LoginInfo>

    @Headers("Content-Type:application/json")
    @POST("auth/users/")
    fun postUserInfo(@Body info: UserInfo): Call<UserInfo>


}