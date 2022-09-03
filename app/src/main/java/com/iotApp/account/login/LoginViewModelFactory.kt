package com.iotApp.account.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iotApp.account.data.AccountRepository

class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                accountRepository = AccountRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
