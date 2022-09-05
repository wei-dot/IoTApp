package com.iotApp.api

import com.iotApp.Constants
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type:application/json")
    @GET(Constants.GET_MODE_KEY_DATA)
    fun getModeKeyDataInfo(@Header("Authorization") token: String): Call<ArrayList<GetModeKeyDataInfo>>


    @Headers("Content-Type:application/json")
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

}