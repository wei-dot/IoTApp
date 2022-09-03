package com.iotApp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

@SuppressLint("CustomSplashScreen")
class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition{true}
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }, 2000)

//        if (SessionManager(this).fetchAuthToken() == null) {
//            Handler(Looper.getMainLooper()).postDelayed({
//                finish()
//                startActivity(Intent(this, MainActivity::class.java))
//            }, 2000)
//        } else {
////            IotApi.getInfo(this, SessionManager(this))
//            IotApi.handler = object : Handler(Looper.getMainLooper()) {
//                override fun handleMessage(msg: Message) {
//                    super.handleMessage(msg)
//                    if (msg.obj != null) {
//                        val response = msg.obj as UserInfo
//                        SessionManager(this@SplashActivity).saveUserInfo(response)
//                    }else{
//                        SessionManager(this@SplashActivity).logout()
//                    }
//                    //exit SplashActivity
//                    Handler(Looper.getMainLooper()).postDelayed({
//                        finish()
//                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
//                    }, 2000)
//                }
//            }
//        }

    }

}