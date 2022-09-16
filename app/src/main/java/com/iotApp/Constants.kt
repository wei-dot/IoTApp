package com.iotApp

object Constants {

    private const val HOST = "api.bap5.cc"

    const val BASE_URL = "https://$HOST/"

    //    const val BASE_URL = "http://$HOST/"
    const val WEB_URL = "wss://$HOST/"

    /**
     * 用戶登錄網址
     */
    const val LOGIN_URL = "auth/token/login"
    const val Signup_URL = "auth/users/"
    const val GET_USER_URL = "auth/users/me/"
    const val LOGOUT_URL = "auth/token/logout/"
    const val RESET_PASSWORD_URL = "auth/users/reset_password/"
    const val SET_PASSWORD_URL = "auth/users/set_password/"
    const val RESEND_ACTIVATION_URL = "auth/users/resend_activation/"

    /**
     * 組合鍵網址
     */
    const val GET_MODE_KEY_DATA = "auth/mode_key_data/"
    const val POST_MODE_KEY_DATA = "auth/mode_key_data/"
    const val DEL_MODE_KEY_DATA = "auth/mode_key_data/{id}/"

    /**
     * 家庭登入網址
     */
    const val FAMILY_URL = "/auth/home_list/"
    const val FAMILY_URL_ID = "/auth/home_list/{id}/"
    const val FAMILY_ADMIN_URL = "/auth/home_admin/"
    const val CHAT_ROOM_URL = "/auth/chat_room/{room_name}/"
    const val MESSAGE_URL = "/auth/chat_room_data/{id}/"

    /**
     * 設備網址
     */
    const val DEVICE_URL = "/auth/device/"
    const val DEVICE_ID_URL = "/auth/device/{id}/"

    const val weather_URL =
        "https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-C0032-001?Authorization=CWB-FF475748-282F-4E9F-81CD-89D15DE20B89&format=JSON&locationName=%E8%87%BA%E5%8C%97%E5%B8%82&elementName=&sort=time"
    const val WEBSOCKET_URL = "ws/device/temp/"
    const val CHAT_URL = "ws/chat/"
    const val IR_URL = "ws/chat/ir/"
    const val DEVICE_DATA_URL = "/auth/device_data/"
}