package com.iotApp.api

import com.iotApp.Constants
import com.iotApp.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    companion object {
        fun getApi(): ApiService? {
            return ApiClient.client?.create(ApiService::class.java)
        }
    }

    @Headers("Content-Type:application/json")
    @GET(Constants.GET_MODE_KEY_DATA)
    fun getModeKeyDataInfo(@Header("Authorization") token: String): Call<ArrayList<GetModeKeyDataInfo>>

    @Headers("Content-Type:application/json")
    @GET(Constants.GET_USER_URL)
    fun getInfo(@Header("Authorization") token: String): Call<UserInfo>

    @Headers("Content-Type:application/json")
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @Headers("Content-Type:application/json")
    @POST(Constants.LOGOUT_URL)
    suspend fun logout(@Header("Authorization") token: String): Response<Void>

    @Headers("Content-Type:application/json")
    @POST(Constants.Signup_URL)
    suspend fun signup(@Body info: UserInfo): Response<UserInfo>

    @Headers("Content-Type:application/json")
    @POST(Constants.RESET_PASSWORD_URL)
    suspend fun resetPassword(@Body info: SendEmail): Response<Void>

    @Headers("Content-Type:application/json")
    @POST(Constants.SET_PASSWORD_URL)
    suspend fun setPassword(
        @Header("Authorization") token: String,
        @Body info: SetPassword
    ): Response<Void>

    @Headers("Content-Type:application/json")
    @POST(Constants.RESEND_ACTIVATION_URL)
    suspend fun resendEmail(@Body info: SendEmail): Response<Void>

    @Headers("Content-Type:application/json")
    @POST(Constants.FAMILY_URL)
    fun createFamily(@Header("Authorization") token: String, @Body info: CreateHome): Call<Void>

    @Headers("Content-Type:application/json")
    @GET(Constants.FAMILY_URL)
    fun getFamily(@Header("Authorization") token: String): Call<ArrayList<Home>>

    @Headers("Content-Type:application/json")
    @GET(Constants.FAMILY_ADMIN_URL)
    fun getFamilyAdmin(@Header("Authorization") token: String): Call<ArrayList<FamilyAdmin>>

    @Headers("Content-Type:application/json")
    @PUT(Constants.FAMILY_URL_ID)
    fun alterFamily(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body info: AlterHome
    ): Call<Void>

    @Headers("Content-Type:application/json")
    @DELETE(Constants.FAMILY_URL_ID)
    fun deleteFamily(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Call<Void>

    @Headers("Content-Type:application/json")
    @GET(Constants.FAMILY_URL_ID)
    fun getFamilyMember(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Call<Home>

    @Headers("Content-Type:application/json")
    @GET(Constants.CHAT_ROOM_URL)
    fun getChatRoomHistory(
        @Path("room_name") room_name: String,
        @Header("Authorization") token: String
    ): Call<ChatRoomHistory>

    @Headers("Content-Type:application/json")
    @GET(Constants.MESSAGE_URL)
    fun getMessageContent(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Call<MessageContent>

    @DELETE(Constants.DEL_MODE_KEY_DATA)
    fun deleteModeKey(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
    ): Call<Void>

    @Headers("Content-Type:application/json")
    @POST(Constants.POST_MODE_KEY_DATA)
    fun postModeKeyDataInfo(
        @Header("Authorization") token: String,
        @Body info: PostModeKeyDataInfo
    ): Call<Void>

    @Headers("Content-Type:application/json")
    @POST(Constants.DEVICE_URL)
    suspend fun addDevice(
        @Header("Authorization") token: String,
        @Body info: Device
    ): Response<Device>

    @Headers("Content-Type:application/json")
    @GET(Constants.DEVICE_URL)
    suspend fun getDevice(
        @Header("Authorization") token: String
    ): Response<ArrayList<Device>>

    @DELETE(Constants.DEVICE_ID_URL)
    suspend fun deleteDevice(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Response<Void>

    @Headers("Content-Type:application/json")
    @GET(Constants.DEVICE_DATA_URL)
    suspend fun getDeviceData(
        @Header("Authorization") token: String,
        ): Response<ArrayList<DeviceData>>
}
