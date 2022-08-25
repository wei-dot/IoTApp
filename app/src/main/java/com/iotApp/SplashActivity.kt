package com.iotApp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.iotApp.api.IotApi
import com.iotApp.api.SessionManager
import com.iotApp.api.UserInfo

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (SessionManager(this).fetchAuthToken() == null) {
            Handler(Looper.getMainLooper()).postDelayed({
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }, 2000)
        } else {
            IotApi.getInfo(this, SessionManager(this))
            IotApi.handler = object : Handler(Looper.getMainLooper()) {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    if (msg.obj != null) {
                        val response = msg.obj as UserInfo
                        intent.putExtra("userInfo", response)
                        Log.d("userInfo", response.toString())
                        SessionManager(this@SplashActivity).saveUserName(response.username)
                    }
                    //exit SplashActivity
                    finish()
                    startActivity(intent)
                }
            }
        }

    }

}