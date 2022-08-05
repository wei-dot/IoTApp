package com.example.iotapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.iotapp.api.IotApi

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        IotApi().getInfo(this)
        Handler(Looper.getMainLooper()).postDelayed({
            // Your Code
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)

    }
}