package com.example.iotapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.iotapp.api.MyAPIService
import com.example.iotapp.api.RetrofitManager
import com.example.iotapp.databinding.SignupBinding
import retrofit2.Call
import retrofit2.Callback

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: SignupBinding
    private var myAPIService: MyAPIService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myAPIService = RetrofitManager.getInstance().create(MyAPIService::class.java)

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnSend.setOnClickListener {
            binding.loading?.isVisible = true
            val username: String = binding.inputUsername.text.toString()
            var password = ""
            if (binding.inputPassword.text.contentEquals(binding.inputConfirmPassword.text)) {
                password = binding.inputPassword.text.toString()
            }
            val email: String = binding.inputEmail.text.toString()
            val user = UserInfo(username, password, "", "", email)
            myAPIService?.postUserInfo(user)?.enqueue(object : Callback<UserInfo> {
                override fun onResponse(
                    call: Call<UserInfo>,
                    response: retrofit2.Response<UserInfo>
                ) {
                    if (response.isSuccessful) {
                        binding.loading?.isVisible = false
                        Log.d("SignupActivity", "註冊成功")
                        Toast.makeText(
                            this@SignupActivity,
                            "註冊成功",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        binding.loading?.isVisible = false
                        Log.d("SignupActivity", "註冊失敗")
                        Toast.makeText(
                            this@SignupActivity,
                            "註冊失敗",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                    binding.loading?.isVisible = false
                    Toast.makeText(
                        this@SignupActivity,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

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
    }

}

