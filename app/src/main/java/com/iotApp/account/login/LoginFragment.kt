package com.iotApp.account.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iotApp.MainActivity
import com.iotApp.R
import com.iotApp.api.IotApi
import com.iotApp.databinding.FragmentAccountLoginBinding
import com.iotApp.model.Login
import com.iotApp.model.LoginResponse
import com.iotApp.model.UserInfo
import com.iotApp.repository.SessionManager

class LoginFragment : Fragment() {

    private var _binding: FragmentAccountLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            activity?.finish()
            startActivity(Intent(activity, MainActivity::class.java))
        }
        binding.btnSend.setOnClickListener {
            val username = binding.tilEmail.editText?.text.toString()
            val password = binding.tilPassword.editText?.text.toString()
            val login = Login(username, password)
            binding.loading.isVisible = true
            binding.btnSend.isEnabled = false
            binding.btnBack.isEnabled = false
            binding.textForgotPassword.isEnabled = false
            binding.textSignup.isEnabled = false
            IotApi.login(login, requireActivity())
            onLogin()
        }
        binding.textForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_resetPasswordFragment)
        }
        binding.textSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        Log.d("System", "Fragment back pressed invoked")
                        activity?.finish()
                        startActivity(Intent(activity, MainActivity::class.java))
                    }
                }
            )
    }

    private fun onLogin() {
        IotApi.handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                val intent = Intent(activity, MainActivity::class.java)
                if (msg.obj != null) {
                    val response = msg.obj as LoginResponse
                    SessionManager(requireActivity()).saveAuthToken(response.authToken)
                    IotApi.getInfo(requireActivity(), SessionManager(requireActivity()))
                    IotApi.handler = object : Handler(Looper.getMainLooper()) {
                        override fun handleMessage(msg: Message) {
                            super.handleMessage(msg)
                            binding.loading.isVisible = false
                            if (msg.obj != null) {
                                val userinfo = msg.obj as UserInfo
                                SessionManager(requireActivity()).saveUserInfo(userinfo)
                            }
                            activity?.finish()
                            startActivity(intent)
                        }
                    }
                } else {
                    binding.loading.isVisible = false
                    binding.btnSend.isEnabled = true
                    binding.btnBack.isEnabled = true
                    binding.textForgotPassword.isEnabled = true
                    binding.textSignup.isEnabled = true
                }
            }
        }
    }
}