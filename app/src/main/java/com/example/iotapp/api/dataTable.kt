package com.example.iotapp

data class UserInfo(
    val user_name: String,
    val user_password: String,
    val user_nickname: String,
    val user_phone: String,
    val user_email: String
)
data class Category(
    val category_name: String,
    val category_description: String,
)