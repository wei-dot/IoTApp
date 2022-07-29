package com.example.iotapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.iotapp.databinding.ActivityLoginBinding
import com.example.iotapp.ui.login.LoginFragment
import com.example.iotapp.ui.signup.SignupFragment

class AccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            when (intent.getStringExtra("Login")) {
                "Login" -> supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_login, LoginFragment())
                    .commitNow()
                "Signup" -> supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_login, SignupFragment())
                    .commitNow()
            }
        }

    }
}