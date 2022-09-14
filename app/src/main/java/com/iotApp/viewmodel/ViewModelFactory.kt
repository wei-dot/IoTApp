package com.iotApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iotApp.repository.AccountRepository
import com.iotApp.repository.DeviceRepository

class ViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        with(modelClass) {
            when {
                isAssignableFrom(DeviceViewModel::class.java) -> return DeviceViewModel(
                    deviceRepository = DeviceRepository()
                ) as T
                isAssignableFrom(AccountViewModel::class.java) -> return AccountViewModel(
                    accountRepository = AccountRepository()
                ) as T
                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        }


    }


}