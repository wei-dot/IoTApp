package com.iotApp.main.family

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MainFamilyViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply { value = "尚未加入家庭" }
    val text: LiveData<String> = _text
}