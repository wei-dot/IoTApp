package com.iotApp.account.resend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iotApp.account.data.AccountRepository

class ResendViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResendViewModel::class.java)) {
            return ResendViewModel(
                accountRepository = AccountRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}