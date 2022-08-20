package com.example.iotapp.api

import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type:application/json")
    @GET(Constants.GET_MODE_KEY_DATA)
    fun getModeKeyDataInfo(@Header("Authorization") token: String): Call<GetModeKeyDataInfo>

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
    fun resetPassword(@Body info: SendEmail): Call<Void>

    @Headers("Content-Type:application/json")
    @POST(Constants.SET_PASSWORD_URL)
    fun setPassword(@Header("Authorization") token: String, @Body info: SetPassword): Call<Void>

    @Headers("Content-Type:application/json")
    @POST(Constants.RESEND_ACTIVATION_URL)
    fun resendEmail(@Body info: SendEmail): Call<Void>

    @Headers("Content-Type:application/json")
    @POST(Constants.FAMILY_URL)
    fun createFamily(@Header("Authorization") token: String, @Body info: CreateHome): Call<Void>

    @Headers("Content-Type:application/json")
    @GET(Constants.FAMILY_URL)
    fun getFamily(@Header("Authorization") token: String): Call<ArrayList<Home>>

    @Headers("Content-Type:application/json")
    @GET(Constants.FAMILY_ADMIN_URL)
    fun getFamilyAdmin(@Header("Authorization") token: String): Call<FamilyAdmin>

    @Headers("Content-Type:application/json")
    @POST(Constants.POST_MODE_KEY_DATA)
    fun postModeKeyDataInfo(@Header("Authorization") token: String, @Body info: PostModeKeyDataInfo): Call<Void>

}