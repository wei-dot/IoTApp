package com.iotApp.repository

import com.iotApp.api.ApiService
import com.iotApp.model.AddDevice
import retrofit2.Response

class DeviceRepository {
    suspend fun addDevice(token:String,addDevice: AddDevice):Response<AddDevice>? = ApiService.getApi()?.addDevice(token=token,info = addDevice)
//    suspend fun getDevice() = 1
//    suspend fun deleteDevice() = 1
//    suspend fun getDeviceData() = 1
//    suspend fun addDeviceData() = 1

}