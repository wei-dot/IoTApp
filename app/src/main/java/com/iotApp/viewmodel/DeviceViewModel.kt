package com.iotApp.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.espressif.iot.esptouch2.provision.EspProvisioner
import com.espressif.iot.esptouch2.provision.EspProvisioningListener
import com.espressif.iot.esptouch2.provision.EspProvisioningRequest
import com.espressif.iot.esptouch2.provision.EspProvisioningResult
import com.iotApp.api.BaseResponse
import com.iotApp.model.Device
import com.iotApp.model.DeviceData
import com.iotApp.repository.DeviceRepository
import kotlinx.coroutines.launch

class DeviceViewModel(private val deviceRepository: DeviceRepository) : ViewModel() {
    private lateinit var espProvisioner: EspProvisioner

    val deviceList: MutableLiveData<BaseResponse<ArrayList<Device>>> = MutableLiveData()
    val addDevice: MutableLiveData<BaseResponse<Device>> = MutableLiveData()
    val deviceDataList: MutableLiveData<BaseResponse<ArrayList<DeviceData>>> = MutableLiveData()

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
                Toast.makeText(context, "配對設備中", Toast.LENGTH_SHORT).show()
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

    fun getDeviceData(token: String) {
        deviceDataList.postValue(BaseResponse.Loading())
        viewModelScope.launch {
            try {
                val response = deviceRepository.getDeviceData(token)
                if (response != null) {
                    if (response.isSuccessful) {
                        deviceDataList.postValue(BaseResponse.Success(response.body()))
                    } else {
                        deviceDataList.postValue(BaseResponse.Error(response.message()))
                    }
                }
            } catch (ex: Exception) {
                deviceDataList.postValue(BaseResponse.Error(ex.message))
            }
        }

    }

    fun deleteDevice(context: Context, token: String, id: String) {
        viewModelScope.launch {
            try {
                val response = deviceRepository.deleteDevice(token, id)
                if (response != null) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Device deleted successfully", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(context, "Device not deleted", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (ex: Exception) {
                Toast.makeText(context, "Device not deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun changeDeviceName(context: Context, id: String, token: String, name: Device) {
        viewModelScope.launch {
            try {
                val response = deviceRepository.changeDeviceName(token = token, deviceId = id, name)
                if (response != null) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "修改設備名稱OK", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(context, "修改設備名稱失敗", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (ex: Exception) {
                Toast.makeText(context, "修改設備名稱錯誤", Toast.LENGTH_SHORT).show()
            }
        }


    }

}