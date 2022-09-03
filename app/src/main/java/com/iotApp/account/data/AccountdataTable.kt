package com.iotApp.account.data

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("auth_token")
    var authToken: String
)

data class LoginRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)

data class UserInfo(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("re_password")
    val re_password: String,
    @SerializedName("email")
    val email: String
)

data class SendEmail(
    @SerializedName("email")
    var email: String
)

data class SetPassword(
    @SerializedName("current_password")
    val password_old: String,
    @SerializedName("new_password")
    val password: String,
    @SerializedName("re_new_password")
    val re_password: String
)


