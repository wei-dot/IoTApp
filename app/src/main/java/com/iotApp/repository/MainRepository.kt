package com.iotApp.repository

import com.iotApp.api.data.UserInfo
import com.iotApp.api.MainApi
import retrofit2.Response

class MainRepository {

    suspend fun userProfile(token: String): Response<UserInfo>? =
        MainApi.getApi()?.getInfo(token = token)
}