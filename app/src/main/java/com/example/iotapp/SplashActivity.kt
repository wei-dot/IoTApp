package com.example.iotapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.iotapp.api.IotApi
import com.example.iotapp.databinding.ActivityMainBinding

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        IotApi().getInfo(this)
        Thread.sleep(3000)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}