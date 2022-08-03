package com.example.iotapp.api

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.example.iotapp.MainActivity
import com.example.iotapp.databinding.FragmentLoginBinding
import com.example.iotapp.databinding.FragmentSignupBinding
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
        signup: FragmentSignupBinding
    ) {
        apiClient.signup(info).enqueue {
            onResponse = {
                if (it.isSuccessful) {
                    signup.loading.isVisible = false
                    val user: UserInfo = it.body()!!
                    Log.d("IotApi", "postInfo: 註冊成功")
                    Toast.makeText(activity, "註冊成功", Toast.LENGTH_SHORT).show()
                    Log.d("IotApi", user.toString())
                    activity?.finish()
                } else {
                    signup.loading.isVisible = false
                    Log.d("IotApi", "postInfo: 註冊失敗")
                    Toast.makeText(
                        activity,
                        "註冊失敗: ${it.errorBody()?.string()} ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            onFailure = {
                signup.loading.isVisible = false

                Log.d("IotApi", "postInfo: ${it?.message}")
                Toast.makeText(activity, "註冊錯誤", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun login(
        @Body info: Login, activity: FragmentActivity?,
        login: FragmentLoginBinding
    ) {
        sessionManager = SessionManager(activity!!)


        apiClient.login(info).enqueue {
            onResponse = {
                val loginResponse = it.body()

                if (it.isSuccessful) {
                    login.loading.isVisible = false
                    sessionManager.saveAuthToken(loginResponse!!.authToken)
                    Toast.makeText(activity, "登入成功", Toast.LENGTH_SHORT).show()
                    Log.d("IotApi Token:", loginResponse.toString())
                } else {
                    login.loading.isVisible = false
                    Log.d("IotApi", "getToken: 登入失敗")
                    Toast.makeText(
                        activity,
                        "登入失敗: ${it.errorBody()?.string()} ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            onFailure = {
                login.loading.isVisible = false
                Log.d("IotApi", "getToken: ${it?.message}")
                Toast.makeText(activity, "登入錯誤", Toast.LENGTH_SHORT).show()
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            // Your Code
            getInfo(activity)
        }, 3000)


    }

    fun getInfo(activity: FragmentActivity?) {

        apiClient = ApiClient.getApiService()
        sessionManager = SessionManager(activity!!)
        apiClient.getInfo(token = "Token ${sessionManager.fetchAuthToken()}")
            .enqueue {
                onResponse = {
                    if (it.isSuccessful) {
                        Log.d("IotApi", "getInfo: 取得成功")
                        val user = it.body()!!
                        Log.d("IotApi", user.toString())
                        Toast.makeText(activity, "歡迎 ${user!!.username} 回來", Toast.LENGTH_SHORT)
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
                    Toast.makeText(activity, "取得失敗", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun logout(activity: MainActivity) {
        sessionManager = SessionManager(activity)
        apiClient.logout(token = "Token ${sessionManager.fetchAuthToken()}")
            .enqueue(object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("IotApi", "logout: ${t.message}")
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Log.d("IotApi", "logout: 登出成功")
                        globalVar = false
                    } else {
                        Log.d("IotApi", "logout: 登出失敗")
                    }
                }
            })
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
//            thread {
//                try {
//                    val jdbcUrl: String = "資料庫位址"
//                    val dbUser = "帳號"
//                    val dbUserPassword = "密碼"
//                    Log.v("DB", "加載驅動成功 ")
//                    val connect = DriverManager.getConnection(jdbcUrl, dbUser, dbUserPassword)
//                    val command = String.format(
//                        "INSERT INTO `account` (`uid`, `id`, `password`, `email`, `phone_num`, `authority`) VALUES ('2', '%s', '%s', '%s', '0920190409', 'temp')",
//                        username,
//                        password,
//                        email
//                    )
//                    val query = connect.prepareStatement(command)
//                    query.execute()
//                    connect.close()
//                } catch (e: SQLException) {
//                    Log.e("DB", "加載驅動失敗 ")
//                    Log.e("DB", e.toString())
//                }
//
//            }
}


