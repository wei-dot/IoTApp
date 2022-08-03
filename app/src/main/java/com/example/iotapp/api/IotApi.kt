package com.example.iotapp.api

import android.util.Log
import android.widget.Toast
import androidx.annotation.MainThread
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
    private lateinit var apiClient: ApiService


    fun signup(
        @Body info: UserInfo,
        activity: FragmentActivity?,
        signup: FragmentSignupBinding
    ) {
        apiClient = ApiClient.getApiService()
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
        apiClient = ApiClient.getApiService()
        sessionManager = SessionManager(activity!!)
        apiClient.login(info).enqueue {
            onResponse = {
                val loginResponse = it.body()

                if (it.isSuccessful) {
                    login.loading.isVisible = false
                    sessionManager.saveAuthToken(loginResponse!!.authToken)
                    Toast.makeText(activity, "登入成功", Toast.LENGTH_SHORT).show()
                    Log.d("IotApi Token:", loginResponse.toString())
                    activity.finish()
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

    }

    fun getInfo(activity: FragmentActivity?): Boolean {
        var isLogin = false
        apiClient = ApiClient.getApiService()
        sessionManager = SessionManager(activity!!)
        apiClient.getinfo(token = "Token ${sessionManager.fetchAuthToken()}")
            .enqueue {
                onResponse = {
                    if (it.isSuccessful) {
                        Log.d("IotApi", "getInfo: 取得成功")
                        val user = it.body()!!
                        isLogin = true
                        Log.d("IotApi", user.toString())
                        Toast.makeText(activity, "歡迎 ${user!!.username} 回來", Toast.LENGTH_SHORT)
                            .show()

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
        return isLogin
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


