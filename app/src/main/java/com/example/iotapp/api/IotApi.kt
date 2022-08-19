package com.example.iotapp.api

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.example.iotapp.MainActivity
import com.example.iotapp.R
import com.example.iotapp.databinding.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body


class IotApi {


    companion object {
        private val apiClient: ApiService = ApiClient.getApiService()
        var handler: Handler = Handler(Looper.getMainLooper())


        /**
         * Function to sign up user
         */
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

        /**
         * Function to login user and get token
         */
        fun login(
            @Body info: Login, activity: FragmentActivity?,
        ) {
            apiClient.login(info).enqueue {
                onResponse = {
                    val loginResponse = it.body()
                    val msg = Message()
                    if (it.isSuccessful) {
                        Toast.makeText(activity, "登入成功", Toast.LENGTH_SHORT).show()
                        Log.d("IotApi Token:", loginResponse.toString())
                        msg.obj = loginResponse
                        handler.sendMessage(msg)
                    } else {
                        Log.d("IotApi", "getToken: 登入失敗")
                        Toast.makeText(
                            activity,
                            "登入失敗: ${it.errorBody()?.string()} ",
                            Toast.LENGTH_SHORT
                        ).show()
                        msg.obj = null
                        handler.sendMessage(msg)
                    }
                }
                onFailure = {
                    Log.d("IotApi", "getToken: ${it?.message}")
                    Toast.makeText(activity, "登入錯誤", Toast.LENGTH_SHORT).show()
                }
            }

        }

        /**
         * Function to get UserInfo
         */
        fun getInfo(activity: FragmentActivity?, sessionManager: SessionManager) {
            apiClient.getInfo(token = "Token ${sessionManager.fetchAuthToken()}")
                .enqueue {
                    onResponse = {
                        val msg = Message()
                        if (it.isSuccessful) {
                            Log.d("IotApi", "getInfo: 取得成功")
                            val response = it.body()!!
                            Log.d("IotApi", response.toString())
                            Toast.makeText(
                                activity,
                                "歡迎 ${response.username} 回來",
                                Toast.LENGTH_SHORT
                            ).show()
                            msg.obj = response
                            handler.sendMessage(msg)
                        } else {
                            Log.d("IotApi", "getInfo: 取得失敗")
                            Toast.makeText(
                                activity,
                                "取得失敗: ${it.errorBody()?.string()} ",
                                Toast.LENGTH_SHORT
                            ).show()
                            msg.obj = null
                            handler.sendMessage(msg)
                        }
                    }
                    onFailure = {
                        Log.d("IotApi", "getInfo: ${it?.message}")
                        Toast.makeText(activity, "取得錯誤40", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        /**
         * Function to Logout user and clear token
         */
        fun logout(activity: MainActivity, sessionManager: SessionManager) {
            apiClient.logout(token = "Token ${sessionManager.fetchAuthToken()}")
                .enqueue {
                    onResponse = {
                        if (it.isSuccessful) {
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

        /**
         * Function to Send Reset Password Email
         */
        fun sendResetPassword(
            @Body info: SendEmail,
            activity: FragmentActivity?,
        ) {
            apiClient.resetPassword(info).enqueue {
                onResponse = {
                    if (it.isSuccessful) {
                        Log.d("IotApi", "resetPassword: 重設密碼連結寄送成功")
                        Toast.makeText(activity, "重設密碼連結寄送成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.d("IotApi", "resetPassword: 重設密碼連結寄送失敗")
                        Toast.makeText(
                            activity,
                            "重設密碼連結寄送失敗: ${it.errorBody()?.string()} ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                onFailure = {
                    Log.d("IotApi", "resetPassword: ${it?.message}")
                    Toast.makeText(activity, "重設密碼連結寄送失敗", Toast.LENGTH_SHORT).show()
                }
            }
        }

        /**
         * Function to Reset Password
         */
        fun setPassword(
            @Body info: SetPassword,
            activity: FragmentActivity?,
            binding: FragmentAccountSetBinding,
            sessionManager: SessionManager
        ) {
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


        /**
         * Function to resend Activation Email
         */
        fun resendActivation(@Body info: SendEmail, activity: FragmentActivity?) {
            apiClient.resendEmail(info).enqueue {
                onResponse = {
                    if (it.isSuccessful) {
                        Log.d("IotApi", "resendActivation: 重新寄送激活信成功")
                        Toast.makeText(activity, "重新寄送激活信成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.d("IotApi", "resendActivation: 重新寄送激活信失敗")
                        Toast.makeText(
                            activity,
                            "重新寄送激活信失敗: ${it.errorBody()?.string()} ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                onFailure = {
                    Log.d("IotApi", "resendActivation: ${it?.message}")
                    Toast.makeText(activity, "重新寄送激活信失敗", Toast.LENGTH_SHORT).show()
                }
            }
        }

//        fun createHome(@Body info: Home,
//                       activity: FragmentActivity?,
//                       binding: /*todo*/,
//                       sessionManager: SessionManager) {
//            apiClient.createFamily(token = "Token ${sessionManager.fetchAuthToken()}", info).enqueue {
//                onResponse = {
//                    if (it.isSuccessful) {
//                        binding.loading?.isVisible = false
//                        Log.d("IotApi", "createHome: 建立家庭成功")
//                        Toast.makeText(activity, "建立家庭成功", Toast.LENGTH_SHORT).show()
//                    } else {
//                        binding.loading?.isVisible = false
//                        Log.d("IotApi", "createHome: 建立家庭失敗")
//                        Toast.makeText(
//                            activity,
//                            "建立家庭失敗: ${it.errorBody()?.string()} ",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//                onFailure = {
//                    binding.loading?.isVisible = false
//                    Log.d("IotApi", "createHome: ${it?.message}")
//                    Toast.makeText(activity, "建立家庭失敗", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
        fun getFamily(activity: FragmentActivity?,
                      binding: UserProfileBinding,
                      sessionManager: SessionManager) {
            apiClient.getFamily(token = "Token ${sessionManager.fetchAuthToken()}").enqueue {
                onResponse = {
                    if (it.isSuccessful) {
                        val response = it.body()!!
                        Log.d("IotApi", response.toString())
                        var familyList : List<String> = response.map { it.home_name }
                        Log.d("IotApi", familyList.toString())
                        val myFamilyList : LinearLayout? = binding.myFamilyList

                        familyList.forEach {
                            val familyItem = View.inflate(activity, com.example.iotapp.R.layout.family_item, null)
                            familyItem.findViewById<TextView>(com.example.iotapp.R.id.family_name).text = it
                            familyItem.setPadding(0, 30, 0, 30)
                            myFamilyList?.addView(familyItem)

                            if (sessionManager.fetchFamilyName() == null){
                                sessionManager.saveFamilyName(familyList[0])
                                familyItem.findViewById<ImageView>(R.id.now_family).isVisible = true
                                val manberList = response.find { it.home_name == familyList[0] }!!.family_admin
                                sessionManager.storeFamilyMembers(manberList)
                            }
                            else{
                                if (sessionManager.fetchFamilyName() == it){
                                    familyItem.findViewById<ImageView>(R.id.now_family).isVisible = true
                                    val manberList = response.find { it.home_name == sessionManager.fetchFamilyName() }!!.family_admin
                                    sessionManager.storeFamilyMembers(manberList)
                                }
                            }
                            familyItem.setOnClickListener{
                                if (sessionManager.fetchFamilyName() != it.findViewById<TextView>(com.example.iotapp.R.id.family_name).text.toString()) {
                                    sessionManager.saveFamilyName(it.findViewById<TextView>(com.example.iotapp.R.id.family_name).text.toString())
                                    Log.d("IotApi", "getFamily: ${sessionManager.fetchFamilyName()}")
                                    activity?.startActivity(Intent(activity, MainActivity::class.java))
                                }
                                else {
                                    Log.d("IotApi", "getFamily: 已選擇此家庭")
                                    Toast.makeText(activity, "已選擇此家庭", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } else {
//                        binding.loading?.isVisible = false
                        Log.d("IotApi", "getFamily: 取得家庭失敗")
                        Toast.makeText(
                            activity,
                            "取得家庭失敗: ${it.errorBody()?.string()} ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                onFailure = {
//                    binding.loading?.isVisible = false
                    Log.d("IotApi", "getFamily: ${it?.message}")
                    Toast.makeText(activity, "取得家庭失敗", Toast.LENGTH_SHORT).show()
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
}


