package com.iotApp.repository

import com.iotApp.api.ApiService
import com.iotApp.model.*
import retrofit2.Response


class AccountRepository {

    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse>? =
        ApiService.getApi()?.login(loginRequest = loginRequest)

    suspend fun logout(token: String): Response<Void>? =
        ApiService.getApi()?.logout(token = token)


    suspend fun signup(registerRequest: UserInfo): Response<UserInfo>? =
        ApiService.getApi()?.signup(info = registerRequest)


    suspend fun forgotPassword(email: SendEmail): Response<Void>? =
        ApiService.getApi()?.resetPassword(info = email)


    suspend fun setPassword(token: String, password: SetPassword): Response<Void>? =
        ApiService.getApi()?.setPassword(token = token, info = password)

    suspend fun resendActivationEmail(email: SendEmail): Response<Void>? =
        ApiService.getApi()?.resendEmail(info = email)

}
