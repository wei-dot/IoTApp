package com.iotApp.data

import com.iotApp.account.data.AccountApi
import com.iotApp.account.data.UserInfo
import retrofit2.Response

class MainRepository {

    suspend fun userProfile(token: String): Response<UserInfo>? =
        MainApi.getApi()?.getInfo(token = token)
}