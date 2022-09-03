package com.iotApp.account.data

import retrofit2.Response


class AccountRepository {

    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse>? =
        AccountApi.getApi()?.login(loginRequest = loginRequest)

    suspend fun logout(token: String): Response<Void>? =
        AccountApi.getApi()?.logout(token = token)


    suspend fun signup(registerRequest: UserInfo): Response<UserInfo>? =
        AccountApi.getApi()?.signup(info = registerRequest)


    suspend fun userProfile(token: String): Response<UserInfo>? =
        AccountApi.getApi()?.getInfo(token = token)


    suspend fun forgotPassword(email: SendEmail): Response<Void>? =
        AccountApi.getApi()?.resetPassword(info = email)


    suspend fun setPassword(token: String, password: SetPassword): Response<Void>? =
        AccountApi.getApi()?.setPassword(token = token, info = password)

    suspend fun resendActivationEmail(email: SendEmail): Response<Void>? =
        AccountApi.getApi()?.resendEmail(info = email)

}
