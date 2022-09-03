package com.iotApp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {
        private lateinit var apiService: ApiService

        fun getApiService(): ApiService {
            if (!Companion::apiService.isInitialized) {
                val okHttpClient = OkHttpClient.Builder()
                    .connectTimeout(250, TimeUnit.MILLISECONDS)
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()

                apiService = retrofit.create(ApiService::class.java)
            }
            return apiService
        }
    }
}
