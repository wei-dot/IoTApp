package com.iotApp.data

import com.iotApp.account.data.ApiClient
import com.iotApp.account.data.UserInfo
import com.iotApp.api.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MainApi {
    companion object {
        fun getApi(): MainApi? {
            return ApiClient.client?.create(MainApi::class.java)
        }
    }

    @Headers("Content-Type:application/json")
    @GET(Constants.GET_USER_URL)
    suspend fun getInfo(@Header("Authorization") token: String): Response<UserInfo>

    @Headers("Content-Type:application/json")
    @GET(Constants.GET_MODE_KEY_DATA)
    fun getModeKeyDataInfo(@Header("Authorization") token: String): Call<ArrayList<GetModeKeyDataInfo>>


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
    @POST(Constants.FAMILY_ADMIN_URL)
    fun setAdmin(@Header("Authorization") token: String, @Body info: Admin): Call<Void>

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

}