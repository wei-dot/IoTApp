package com.example.iotapp.ui.mode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ModeViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Mode Fragment"
    }
    val text: LiveData<String> = _text
}