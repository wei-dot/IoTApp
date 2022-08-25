package com.iotApp.ui.notLogin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotLoginViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "尚未登入"
    }
    val text: LiveData<String> = _text
}
