package com.iotApp.api

import com.iotApp.Constants
import retrofit2.Call
import retrofit2.http.*

interface FamilyApi {
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