package com.iotApp.account.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iotApp.account.data.AccountRepository

class SignupViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            return SignupViewModel(accountRepository = AccountRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}