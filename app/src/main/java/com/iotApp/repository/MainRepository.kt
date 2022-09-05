package com.iotApp.repository

import com.iotApp.api.ApiInterface
import com.iotApp.api.data.UserInfo
import retrofit2.Response

class MainRepository {

    suspend fun userProfile(token: String): Response<UserInfo>? =
        ApiInterface.getApi()?.getInfo(token = token)
}