package com.iotApp.api

object Constants {

    //    const val BASE_URL = "https://api.bap5.cc/"
    const val BASE_URL = "http://192.168.1.14:8000/" // 伺服器地址

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
    const val Power_Strip_URL = "/ws/strip/temp/"
}