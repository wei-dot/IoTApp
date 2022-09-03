package com.iotApp.account.forget

data class ForgetPasswordFormState(
    val emailError: Int? = null,
    val isDataValid: Boolean = false
)