package com.iotApp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.iotApp.api.IotApi
import com.iotApp.api.SessionManager
import com.iotApp.api.UserInfo
import com.iotApp.service.InviteService

@SuppressLint("CustomSplashScreen")
class SplashActivity : Activity() {
    private lateinit var inviteService: InviteService

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as InviteService.InviteServiceBinder
            inviteService = binder.getService()
        }
        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { true }

        startService(Intent(this, InviteService::class.java))

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
                    if (msg.obj != null) {
                        val response = msg.obj as UserInfo
                        SessionManager(this@SplashActivity).saveUserInfo(response)
                    } else {
                        SessionManager(this@SplashActivity).logout()
                    }
                    //exit SplashActivity
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    }, 2000)
                }
            }
        }

    }

}