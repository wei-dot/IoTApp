package com.example.iotapp.api

object Constants  {
    const val BASE_URL = "https://api.bap5.cc/"
    const val LOGIN_URL = "auth/token/login"
    const val Signup_URL = "auth/users/"
    const val GET_USER_URL = "auth/users/me/"
    const val LOGOUT_URL = "auth/token/logout/"
    const val RESET_PASSWORD_URL = "auth/users/reset_password/"
    const val SET_PASSWORD_URL = "auth/users/set_password/"
    const val RESEND_ACTIVATION_URL = "auth/users/resend_activation/"
    const val GET_MODE_KEY_DATA = "auth/mode_key_data/"
    const val POST_MODE_KEY_DATA = "auth/mode_key_data/"
    const val FAMILY_URL="/auth/home_list/"
    const val FAMILY_ADMIN_URL="/auth/home_admin/"
    const val Power_Strip_URL="/ws/strip/temp/"
}