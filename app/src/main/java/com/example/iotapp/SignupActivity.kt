package com.example.iotapp

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.iotapp.databinding.LoginBinding
import com.example.iotapp.databinding.SignupBinding

class SignupActivity :AppCompatActivity() {
    private  lateinit var binding: SignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener {
            val switchToMainPage: Intent = Intent(this, MainActivity::class.java)
            startActivity(switchToMainPage)
        }
    }
}