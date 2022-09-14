package com.iotApp.model

import com.google.gson.annotations.SerializedName

data class DHT11(
    @SerializedName("temperature")
    val temp: String,
    @SerializedName("humidity")
    val hum: String
)

data class MQ7(
    @SerializedName("COppm")
    val co: String,
)

data class SR501(
    @SerializedName("motion")
    val motion: String
)