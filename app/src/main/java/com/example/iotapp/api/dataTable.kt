package com.example.iotapp.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserInfo (
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
):Serializable

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
data class FamilyAdmin(
    @SerializedName("family_name")
    val family_name: String,
    @SerializedName("family_description")
    val family_description: String,
    @SerializedName("family_admin")
    val family_admin: String,
    @SerializedName("family_admin_phone")
    val family_admin_phone: String,
    @SerializedName("family_admin_email")
    val family_admin_email: String
)
data class Home(
    @SerializedName("user")
    val family_admin: ArrayList<String>,
    @SerializedName("home_name")
    val home_name: String,
    @SerializedName("home_id")
    val id: String
)