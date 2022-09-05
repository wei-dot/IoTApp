package com.iotApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iotApp.repository.AccountRepository

class ViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        with(modelClass) {
            when {
                isAssignableFrom(SignupViewModel::class.java) -> {
                    return SignupViewModel(accountRepository = AccountRepository()) as T
                }
                isAssignableFrom(LoginViewModel::class.java) -> {
                    return LoginViewModel(accountRepository = AccountRepository()) as T
                }
                isAssignableFrom(SetPasswordViewModel::class.java) -> {
                    return SetPasswordViewModel(accountRepository = AccountRepository()) as T
                }
                isAssignableFrom(ResendViewModel::class.java) -> {
                    return ResendViewModel(accountRepository = AccountRepository()) as T
                }
                isAssignableFrom(SetPasswordViewModel::class.java) -> {
                    return SetPasswordViewModel(accountRepository = AccountRepository()) as T
                }
                isAssignableFrom(ForgetPasswordViewModel::class.java) -> {
                    return ForgetPasswordViewModel(accountRepository = AccountRepository()) as T
                }
                else -> {
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            }
        }

    }
}