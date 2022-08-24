package com.example.iotapp.ui.mode

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ModeViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Mode Fragment"
    }
    var tplinkSwitchSet:String = ""

    var AcTemperature:Int = 0
    var AcSwitch:Boolean = false
    var FamilyId:String = ""
    val text: LiveData<String> = _text
    fun setTplinkSwitch(value: String) {
        tplinkSwitchSet = value
    }

    fun getTplinkSwitch(): String {
        return tplinkSwitchSet
    }
    @JvmName("setAcTemperature1")
    fun setAcTemperature(value: Int) {
        AcTemperature = value
    }
    @JvmName("setAcTemperature2")
    fun getAcTemperature(): Int {
        return AcTemperature
    }

    @JvmName("setAcSwitch1")
    fun setAcSwitch(value: Boolean) {
        AcSwitch = value
    }

    @JvmName("getAcSwitch1")
    fun getAcSwitch(): Boolean {
        return AcSwitch
    }
    @JvmName("setFamilyId1")
    fun setFamilyId(value: String) {
        FamilyId = value
    }
    @JvmName("getFamilyId1")
    fun getFamilyId(): String {
        return FamilyId
    }
}