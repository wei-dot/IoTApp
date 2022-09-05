package com.iotApp.view.main.notLogin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainNotLoginViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "尚未登入"
    }
    val text: LiveData<String> = _text
}
