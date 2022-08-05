package com.example.iotapp.api

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("re_password")
    val re_password: String,
    @SerializedName("user_nickname")
    val user_nickname: String,
    @SerializedName("user_phone")
    val user_phone: String,
    @SerializedName("email")
    val email: String
)

data class Category(
    val category_name: String,
    val category_description: String,
)

data class Login(

    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)

data class LoginResponse(
    @SerializedName("auth_token")
    var authToken: String,
)
data class ResetPassword(
    @SerializedName("email")
    val email: String
)