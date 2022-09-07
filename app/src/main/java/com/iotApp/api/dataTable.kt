package com.iotApp.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

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

 data class GetModeKeyDataInfo(
     @SerializedName("mode_key_data_id")
     val mode_key_data_id: Int,
     @SerializedName("mode_key_name")
     val mode_key_name: String,
     @SerializedName("home_id")
     val home_id: Int,
     @SerializedName("tplink_switch_mode_key")
     val tplink_switch_mode_key: String,
     @SerializedName("ac_temperature")
     val ac_temperature: Int,
     @SerializedName("ac_switch")
     val ac_switch: Boolean,
     @SerializedName("mode_key_time")
        val mode_key_time: Date,
 ):Serializable

data class PostModeKeyDataInfo(
    @SerializedName("home_id")
    val home_id: String,
    @SerializedName("tplink_switch_mode_key")
    val tplink_switch_mode_key: String,
    @SerializedName("mode_key_name")
    val mode_key_name: String,
    @SerializedName("ac_temperature")
    val ac_temperature: Int,
    @SerializedName("ac_switch")
    val ac_switch: Boolean,
)

data class Home(
    @SerializedName("user")
    val family_member: ArrayList<String>,
    @SerializedName("home_name")
    val home_name: String,
    @SerializedName("home_id")
    val id: String
)

data class CreateHome(
    @SerializedName("home_name")
    val home_name: String,
    @SerializedName("user")
    val home_admin: ArrayList<String>,
)
data class Admin(
    @SerializedName("home_name")
    val home_name: String,
    @SerializedName("user")
    val user: String
)

data class FamilyAdmin(
    @SerializedName("id")
    val id: String,
    @SerializedName("home")
    val home_id: String,
    @SerializedName("admin")
    val home_admin: String
)

data class AlterHome(
    @SerializedName("home_name")
    val home_name: String,
    @SerializedName("user")
    val user: ArrayList<String>
)

data class ChatRoomHistory(
    @SerializedName("chat_room_name")
    val chat_room_name: String,
    @SerializedName("message")
    val message: ArrayList<String>
)

data class MessageContent(
    @SerializedName("message_id")
    val message_id: String,
    @SerializedName("chat_room_name")
    val chat_room_name: String,
    @SerializedName("message")
    val message: String
)

