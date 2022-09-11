package com.iotApp

import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.iotApp.account.login.LoginFragment
import com.iotApp.databinding.ActivityLoginBinding

class AccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        when (intent.getStringExtra("Login")) {
            "SetPassword" -> {
                findNavController(R.id.nav_host_fragment_activity_login).navigate(R.id.resetPasswordFragment)
            }
            "Signup" -> {
                findNavController(R.id.nav_host_fragment_activity_login).navigate(R.id.action_loginFragment_to_signupFragment)
            }
        }

    }
}