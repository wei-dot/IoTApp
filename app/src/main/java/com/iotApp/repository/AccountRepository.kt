package com.iotApp.repository

import com.iotApp.api.ApiInterface
import com.iotApp.api.data.*
import retrofit2.Response


class AccountRepository {

    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse>? =
        ApiInterface.getApi()?.login(loginRequest = loginRequest)

    suspend fun logout(token: String): Response<Void>? =
        ApiInterface.getApi()?.logout(token = token)


    suspend fun signup(registerRequest: UserInfo): Response<UserInfo>? =
        ApiInterface.getApi()?.signup(info = registerRequest)


    suspend fun forgotPassword(email: SendEmail): Response<Void>? =
        ApiInterface.getApi()?.resetPassword(info = email)


    suspend fun setPassword(token: String, password: SetPassword): Response<Void>? =
        ApiInterface.getApi()?.setPassword(token = token, info = password)

    suspend fun resendActivationEmail(email: SendEmail): Response<Void>? =
        ApiInterface.getApi()?.resendEmail(info = email)

}
