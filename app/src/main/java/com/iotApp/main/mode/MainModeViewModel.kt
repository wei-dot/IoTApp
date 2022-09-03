package com.iotApp.main.mode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ModeViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Mode Fragment"
    }

}