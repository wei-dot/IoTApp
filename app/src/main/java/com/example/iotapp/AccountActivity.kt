package com.example.iotapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.iotapp.databinding.ActivityLoginBinding
import com.example.iotapp.ui.login.LoginFragment
import com.example.iotapp.ui.signup.SignupFragment

class AccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (intent.getStringExtra("Login")=="Signup"){
            findNavController(R.id.nav_host_fragment_activity_login).navigate(R.id.signupFragment)
        }

    }
}