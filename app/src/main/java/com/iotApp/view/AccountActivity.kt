package com.iotApp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.iotApp.R
import com.iotApp.databinding.ActivityLoginBinding
import com.iotApp.viewmodel.AccountViewModel
import com.iotApp.viewmodel.ViewModelFactory

class AccountActivity : AppCompatActivity() {
    private lateinit var viewModel: AccountViewModel
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
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory()
        )[AccountViewModel::class.java]

    }
}