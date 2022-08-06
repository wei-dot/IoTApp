package com.example.iotapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.iotapp.MainActivity
import com.example.iotapp.R
import com.example.iotapp.api.IotApi
import com.example.iotapp.api.Login
import com.example.iotapp.databinding.FragmentAccountLoginBinding

class LoginFragment : Fragment() {

    private var _binging: FragmentAccountLoginBinding? = null
    private val binding get() = _binging!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binging = FragmentAccountLoginBinding.inflate(inflater, container, false)
        val root = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()
        }
        binding.btnSend.setOnClickListener {
            val username = binding.tilEmail?.editText?.text.toString()
            val password = binding.tilPassword?.editText?.text.toString()
            Log.d("LoginFragment", "username: $username, password: $password")
            val login = Login(username, password)
            binding.loading.isVisible = true
            binding.btnSend.isEnabled = false
            binding.btnBack.isEnabled = false
            binding.textForgotPassword.isEnabled = false
            binding.textSignup.isEnabled = false
            IotApi().login(login, activity)

            Handler(Looper.getMainLooper()).postDelayed({
                // Your Code
                binding.loading.isInvisible = false
                activity?.finish()
                startActivity(Intent(activity, MainActivity::class.java))
            }, 3000)


        }
        binding.textForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_resetPasswordFragment)
        }
        binding.textSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }
}