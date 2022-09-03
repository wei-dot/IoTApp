package com.iotApp.main.log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainLogViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "尚未加入設備"
    }
    val text: LiveData<String> = _text
}