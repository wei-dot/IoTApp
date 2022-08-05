package com.example.iotapp.api

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.example.iotapp.MainActivity
import com.example.iotapp.databinding.FragmentAccountForgetBinding
import com.example.iotapp.databinding.FragmentAccountSetBinding
import com.example.iotapp.databinding.FragmentAccountSignupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body


class IotApi {


    private lateinit var sessionManager: SessionManager

    companion object {
        var apiClient: ApiService = ApiClient.getApiService()
        var globalVar = false
    }

    fun signup(
        @Body info: UserInfo,
        activity: FragmentActivity?,
        binding: FragmentAccountSignupBinding
    ) {
        apiClient.signup(info).enqueue {
            onResponse = {
                if (it.isSuccessful) {
                    binding.loading.isVisible = false
                    val user: UserInfo = it.body()!!
                    Log.d("IotApi", "postInfo: 註冊成功")
                    Toast.makeText(activity, "註冊成功", Toast.LENGTH_SHORT).show()
                    Log.d("IotApi", user.toString())
                    activity?.finish()
                } else {
                    binding.loading.isVisible = false
                    Log.d("IotApi", "postInfo: 註冊失敗")
                    Toast.makeText(
                        activity,
                        "註冊失敗: ${it.errorBody()?.string()} ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            onFailure = {
                binding.loading.isVisible = false

                Log.d("IotApi", "postInfo: ${it?.message}")
                Toast.makeText(activity, "註冊錯誤", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun login(
        @Body info: Login, activity: FragmentActivity?,
    ) {
        sessionManager = SessionManager(activity!!)
        apiClient.login(info).enqueue {
            onResponse = {
                val loginResponse = it.body()
                if (it.isSuccessful) {
                    sessionManager.saveAuthToken(loginResponse!!.authToken)
                    Toast.makeText(activity, "登入成功", Toast.LENGTH_SHORT).show()
                    Log.d("IotApi Token:", loginResponse.toString())
                } else {
                    Log.d("IotApi", "getToken: 登入失敗")
                    Toast.makeText(
                        activity,
                        "登入失敗: ${it.errorBody()?.string()} ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            onFailure = {
                Log.d("IotApi", "getToken: ${it?.message}")
                Toast.makeText(activity, "登入錯誤", Toast.LENGTH_SHORT).show()
            }
        }
        Handler(Looper.getMainLooper()).postDelayed({
            // Your Code
            getInfo(activity)
        }, 2000)
    }

    fun getInfo(activity: FragmentActivity?) {
        sessionManager = SessionManager(activity!!)
        apiClient.getInfo(token = "Token ${sessionManager.fetchAuthToken()}")
            .enqueue {
                onResponse = {
                    if (it.isSuccessful) {
                        Log.d("IotApi", "getInfo: 取得成功")
                        val user = it.body()!!
                        Log.d("IotApi", user.toString())
                        Toast.makeText(activity, "歡迎 ${user.username} 回來", Toast.LENGTH_SHORT)
                            .show()
                        globalVar = true
                    } else {
                        Log.d("IotApi", "getInfo: 取得失敗")
                        Toast.makeText(
                            activity,
                            "取得失敗: ${it.errorBody()?.string()} ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                onFailure = {
                    Log.d("IotApi", "getInfo: ${it?.message}")
                    Toast.makeText(activity, "取得錯誤40", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun logout(activity: MainActivity) {
        sessionManager = SessionManager(activity)
        apiClient.logout(token = "Token ${sessionManager.fetchAuthToken()}")
            .enqueue {
                onResponse = {
                    if (it.isSuccessful) {
                        globalVar = false
                        Log.d("IotApi", "logout: 登出成功")
                        Toast.makeText(activity, "登出成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.d("IotApi", "logout: 登出失敗")
                        Toast.makeText(
                            activity,
                            "登出失敗: ${it.errorBody()?.string()} ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                onFailure = {
                    Log.d("IotApi", "logout: ${it?.message}")
                    Toast.makeText(activity, "登出失敗", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun resetPassword(
        @Body info: ResetPassword,
        activity: FragmentActivity?,
        binding: FragmentAccountForgetBinding
    ) {
        apiClient.resetPassword(info).enqueue {
            onResponse = {
                if (it.isSuccessful) {
                    binding.loading.isVisible = false
                    Log.d("IotApi", "resetPassword: 重設密碼連結寄送成功")
                    Toast.makeText(activity, "重設密碼連結寄送成功", Toast.LENGTH_SHORT).show()
                } else {
                    binding.loading.isVisible = false
                    Log.d("IotApi", "resetPassword: 重設密碼連結寄送失敗")
                    Toast.makeText(
                        activity,
                        "重設密碼連結寄送失敗: ${it.errorBody()?.string()} ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            onFailure = {
                binding.loading.isVisible = false
                Log.d("IotApi", "resetPassword: ${it?.message}")
                Toast.makeText(activity, "重設密碼連結寄送失敗", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setPassword(
        @Body info: SetPassword,
        activity: FragmentActivity?,
        binding: FragmentAccountSetBinding
    ) {
        sessionManager = SessionManager(activity!!)
        apiClient.setPassword(token = "Token ${sessionManager.fetchAuthToken()}", info)
            .enqueue {
                onResponse = {

                    if (it.isSuccessful) {
                        binding.loading?.isVisible = false
                        Log.d("IotApi", "setPassword: 設定密碼成功")
                        Toast.makeText(activity, "設定密碼成功", Toast.LENGTH_SHORT).show()
                    } else {
                        binding.loading?.isVisible = false
                        Log.d("IotApi", "setPassword: 設定密碼失敗")
                        Toast.makeText(
                            activity,
                            "設定密碼失敗: ${it.errorBody()?.string()} ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                onFailure = {
                    binding.loading?.isVisible = false
                    Log.d("IotApi", "setPassword: ${it?.message}")
                    Toast.makeText(activity, "設定密碼失敗", Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun <T> Call<T>.enqueue(callback: CallBackKt<T>.() -> Unit) {
        val callBackKt = CallBackKt<T>()
        callback.invoke(callBackKt)
        this.enqueue(callBackKt)
    }

    class CallBackKt<T> : Callback<T> {

        var onResponse: ((Response<T>) -> Unit)? = null
        var onFailure: ((t: Throwable?) -> Unit)? = null

        override fun onFailure(call: Call<T>, t: Throwable) {
            onFailure?.invoke(t)
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            onResponse?.invoke(response)
        }

    }
}


