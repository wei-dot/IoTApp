package com.iotApp.account.set

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iotApp.account.data.AccountRepository

class SetPasswordViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SetPasswordViewModel::class.java)) {
            return SetPasswordViewModel(
                accountRepository = AccountRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}