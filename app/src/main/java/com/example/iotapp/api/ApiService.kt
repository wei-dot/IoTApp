package com.example.iotapp.api

import retrofit2.Call
import retrofit2.http.*

interface ApiService {


    @Headers("Content-Type:application/json")
    @POST(Constants.Signup_URL)
    fun signup(@Body info: UserInfo): Call<UserInfo>

    @Headers("Content-Type:application/json")
    @POST(Constants.LOGIN_URL)
    fun login(@Body info: Login): Call<LoginResponse>

    @Headers("Content-Type:application/json")
    @GET(Constants.GET_USER_URL)
    fun getInfo(@Header("Authorization") token: String): Call<UserInfo>

    @Headers("Content-Type:application/json")
    @POST(Constants.LOGOUT_URL)
    fun logout(@Header("Authorization") token: String): Call<Void>

    @Headers("Content-Type:application/json")
    @POST(Constants.RESET_PASSWORD_URL)
    fun resetPassword(@Body info: ResetPassword): Call<Void>


}