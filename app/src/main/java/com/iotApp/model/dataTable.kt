package com.iotApp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

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
    val home_id: String,
    @SerializedName("tplink_switch_mode_key")
    val tplink_switch_mode_key: String,
    @SerializedName("ac_temperature")
    val ac_temperature: Int,
    @SerializedName("ac_switch")
    val ac_switch: Boolean,
    @SerializedName("fan_level")
    val fan_level: Int,
    @SerializedName("fan_switch")
    val fan_switch: Boolean,
    @SerializedName("fan_spin")
    val fan_spin: Boolean,
    @SerializedName("mode_key_time")
    val mode_key_time: String,
) : Serializable

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
    @SerializedName("fan_level")
    val fan_level: Int,
    @SerializedName("fan_switch")
    val fan_switch: Boolean,
    @SerializedName("fan_spin")
    val fan_spin: Boolean,
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

data class Device(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    var device_name: String,
    @SerializedName("type_name")
    val device_type: String,
    @SerializedName("home")
    val home_id: String,
    @SerializedName("added_time")
    val time: String,
)

data class DeviceData(
    @SerializedName("data_time")
    val time: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("data_status")
    val status: String,
    @SerializedName("type")
    val type: String,
//    @SerializedName("user")
//    val user: String,
//    @SerializedName("home")
//    val home_id: String,
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

