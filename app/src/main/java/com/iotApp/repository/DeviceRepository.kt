package com.iotApp.repository

import com.iotApp.api.ApiService
import com.iotApp.model.Device
import retrofit2.Response

class DeviceRepository {
    suspend fun addDevice(token: String, addDevice: Device): Response<Device>? =
        ApiService.getApi()?.addDevice(token = token, info = addDevice)

    suspend fun getDevice(token: String): Response<Device>? =
        ApiService.getApi()?.getDevice(token = token)

    suspend fun deleteDevice(token: String, deviceId: String): Response<Void>? =
        ApiService.getApi()?.deleteDevice(token = token, id = deviceId)
//    suspend fun deleteDevice() = 1
//    suspend fun getDeviceData() = 1
//    suspend fun addDeviceData() = 1

}