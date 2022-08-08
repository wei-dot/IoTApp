package com.example.iotapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.iotapp.api.IotApi

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        IotApi().getInfo(this)
        Handler(Looper.getMainLooper()).postDelayed({
            // Your Code
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }, 2000)

    }
}