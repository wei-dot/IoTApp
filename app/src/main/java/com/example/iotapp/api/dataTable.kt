package com.example.iotapp.api

data class UserInfo(
    val username: String,
    val password: String,
    val re_password: String,
    val user_nickname: String,
    val user_phone: String,
    val email: String
)
data class Category(
    val category_name: String,
    val category_description: String,
)

data class LoginInfo(
    val username: String,
    val password: String
)
