package com.example.iotapp.ui.log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LogViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is log Fragment"
    }
    val text: LiveData<String> = _text
}