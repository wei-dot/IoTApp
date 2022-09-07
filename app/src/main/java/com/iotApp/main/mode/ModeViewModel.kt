package com.iotApp.main.mode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ModeViewModel : ViewModel() {
    var deleteKetMode: Boolean = false
    private val _text = MutableLiveData<String>().apply {
        value = "This is Mode Fragment"
    }

}