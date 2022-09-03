package com.iotApp.account.data

import com.iotApp.api.Constants
import retrofit2.Response
import retrofit2.http.*

interface AccountApi {
    companion object {
        fun getApi(): AccountApi? {
            return ApiClient.client?.create(AccountApi::class.java)
        }
    }

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
    @GET(Constants.GET_USER_URL)
    suspend fun getInfo(@Header("Authorization") token: String): Response<UserInfo>


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

}
