package com.example.iotapp.ui.family

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class FamilyViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply { value = "尚未加入家庭" }
    val text: LiveData<String> = _text
}