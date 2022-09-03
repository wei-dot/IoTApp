package com.iotApp.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*


data class Category(
    val category_name: String,
    val category_description: String,
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

