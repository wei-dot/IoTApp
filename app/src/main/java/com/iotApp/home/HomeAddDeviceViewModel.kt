package com.iotApp.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.espressif.iot.esptouch2.provision.EspProvisioner
import com.espressif.iot.esptouch2.provision.EspProvisioningListener
import com.espressif.iot.esptouch2.provision.EspProvisioningRequest
import com.espressif.iot.esptouch2.provision.EspProvisioningResult
import com.iotApp.model.AddDevice
import com.iotApp.repository.DeviceRepository
import kotlinx.coroutines.launch

class HomeAddDeviceViewModel(private val deviceRepository: DeviceRepository) : ViewModel() {
    private lateinit var espProvisioner: EspProvisioner

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
    fun addDevice(token:String,info:AddDevice){
        viewModelScope.launch {
            deviceRepository.addDevice(token,info)
        }
    }
}