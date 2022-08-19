package com.example.iotapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import com.example.iotapp.api.IotApi
import com.example.iotapp.api.SessionManager
import com.example.iotapp.api.UserInfo
import com.example.iotapp.ui.family.FamilyFragment

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
                    }

                    //exit SplashActivity
                    finish()
                    startActivity(intent)
                }
            }
        }

    }

}