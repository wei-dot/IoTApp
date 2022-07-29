package com.example.iotapp.api

import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.example.iotapp.databinding.FragmentSignupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body

class IotApi {
    private val run: MyAPIService = RetrofitManager.getInstance().create(MyAPIService::class.java)

    fun postInfo(
        @Body info: UserInfo,
        activity: FragmentActivity?,
        signup: FragmentSignupBinding
    ) {

        run.postUserInfo(info).enqueue {
            onResponse = {
                if (it.isSuccessful) {
                    signup.loading.isVisible = false
                    Log.d("IotApi", "postInfo: 註冊成功")
                    Toast.makeText(activity, "註冊成功", Toast.LENGTH_SHORT).show()
                    activity?.finish()
                } else {
                    signup.loading.isVisible = false
                    Log.d("IotApi", "postInfo: 註冊失敗")
                    Toast.makeText(activity, "註冊失敗", Toast.LENGTH_SHORT).show()
                }
            }
            onFailure = {
                signup.loading.isVisible = false
                Log.d("IotApi", "postInfo: ${it?.message}")
                Toast.makeText(activity, "註冊錯誤", Toast.LENGTH_SHORT).show()
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


