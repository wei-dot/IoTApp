package com.iotApp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.espressif.iot.esptouch2.provision.EspProvisioner
import com.espressif.iot.esptouch2.provision.EspProvisioningListener
import com.espressif.iot.esptouch2.provision.EspProvisioningRequest
import com.espressif.iot.esptouch2.provision.EspProvisioningResult
import com.iotApp.api.BaseResponse
import com.iotApp.model.Device
import com.iotApp.repository.DeviceRepository
import kotlinx.coroutines.launch

class DeviceViewModel(private val deviceRepository: DeviceRepository) : ViewModel() {
    private lateinit var espProvisioner: EspProvisioner

    val deviceList: MutableLiveData<BaseResponse<ArrayList<Device>>> = MutableLiveData()
    val addDevice: MutableLiveData<BaseResponse<Device>> = MutableLiveData()

    fun pairDevice(context: Context, ssid: String, password: String, customData: String) {
        espProvisioner = EspProvisioner(context)
        val request = EspProvisioningRequest.Builder(context)
            .setSSID(ssid.toByteArray())
            .setPassword(password.toByteArray())
            .setReservedData(customData.toByteArray())
            .setAESKey("McQfTjWnZr4u7x!A".toByteArray())
            .build()
        val listener = object : EspProvisioningListener {

            override fun onStart() {
                TODO("Not yet implemented")
            }

            override fun onResponse(result: EspProvisioningResult?) {
                TODO("Not yet implemented")
            }

            override fun onStop() {
                TODO("Not yet implemented")
            }

            override fun onError(e: Exception?) {
                TODO("Not yet implemented")
            }
        }
        espProvisioner.startProvisioning(request, listener)
    }

    fun addDevice(token: String, info: Device) {
        addDevice.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = deviceRepository.addDevice(token, info)
                if (response != null) {
                    if (response.isSuccessful) {
                        addDevice.value = BaseResponse.Success(response.body())
                    } else {
                        addDevice.value = BaseResponse.Error(response.message())
                    }
                }
            } catch (ex: Exception) {
                addDevice.value = BaseResponse.Error(ex.message)
            }

        }
    }

    fun getDevice(token: String) {
        deviceList.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = deviceRepository.getDevice(token)
                if (response != null) {
                    if (response.isSuccessful) {
                        deviceList.value = BaseResponse.Success(response.body())
                    } else {
                        deviceList.value = BaseResponse.Error(response.message())
                    }
                }

            } catch (ex: Exception) {
                deviceList.value = BaseResponse.Error(ex.message)
            }
        }
    }
}