package com.iotApp.view.mode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ModeViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Mode Fragment"
    }
    private var tpLinkSwitchSet: String = ""
    private var acTemperature: Int = 0
    private var acSwitch: Boolean = false
    private var fanLevel: Int = 0
    private var fanSwitch: Boolean = false
    private var fanSpin: Boolean = false
    val text: LiveData<String> = _text
    fun setTpLinkSwitch(value: String) {
        tpLinkSwitchSet = value
    }

    fun getTplinkSwitch(): String {
        return tpLinkSwitchSet
    }

    @JvmName("setAcTemperature1")
    fun setAcTemperature(value: Int) {
        acTemperature = value
    }

    @JvmName("setAcTemperature2")
    fun getAcTemperature(): Int {
        return acTemperature
    }

    @JvmName("setAcSwitch1")
    fun setAcSwitch(value: Boolean) {
        acSwitch = value
    }

    @JvmName("getAcSwitch1")
    fun getAcSwitch(): Boolean {
        return acSwitch
    }


    @JvmName("setFanLevel1")
    fun setFanLevel(value: Int) {
        fanLevel = value
    }

    @JvmName("getFanLevel1")
    fun getFanLevel(): Int {
        return fanLevel
    }

    @JvmName("setFanSwitch1")
    fun setFanSwitch(value: Boolean) {
        fanSwitch = value
    }

    @JvmName("getFanSwitch1")
    fun getFanSwitch(): Boolean {
        return fanSwitch
    }

    @JvmName("setFanSpin1")
    fun setFanSpin(value: Boolean) {
        fanSpin = value
    }

    @JvmName("getFanSpin1")
    fun getFanSpin(): Boolean {
        return fanSpin
    }
}