package com.iotApp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.iotApp.api.IotApi
import com.iotApp.api.SessionManager
import com.iotApp.api.UserInfo

@SuppressLint("CustomSplashScreen")
class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
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
                        SessionManager(this@SplashActivity).saveUserInfo(response)
                    }
                    //exit SplashActivity
                    finish()
                    startActivity(intent)
                }
            }
        }

    }

}