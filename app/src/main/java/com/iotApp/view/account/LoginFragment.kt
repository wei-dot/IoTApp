package com.iotApp.view.account

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.iotApp.R
import com.iotApp.api.BaseResponse
import com.iotApp.api.IotApi
import com.iotApp.databinding.FragmentAccountLoginBinding
import com.iotApp.model.UserInfo
import com.iotApp.repository.SessionManager
import com.iotApp.view.MainActivity
import com.iotApp.viewmodel.AccountViewModel
import com.iotApp.viewmodel.ViewModelFactory
import com.iotApp.viewmodel.afterTextChanged

class LoginFragment : Fragment() {
    private lateinit var viewModel: AccountViewModel

    private var _binding: FragmentAccountLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this@LoginFragment,
            ViewModelFactory()
        )[AccountViewModel::class.java]

        viewModel.loginResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }
                is BaseResponse.Success -> {
                    stopLoading()
                    it.data?.let { it1 ->
                        SessionManager(requireContext()).saveAuthToken(
                            it1.authToken
                        )
                    }
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
                            startActivity(Intent(activity, MainActivity::class.java))
                        }
                    }
                }
                is BaseResponse.Error -> {
                    stopLoading()
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    stopLoading()
                }
            }
        }
        viewModel.accountFormState.observe(viewLifecycleOwner, Observer {
            val loginState = it ?: return@Observer
            binding.login.isEnabled = it.isDataValid
            if (loginState.usernameError != null) {
                binding.tilEmail.error = getString(loginState.usernameError)
            } else {
                binding.tilEmail.error = null
            }
            if (loginState.passwordError != null) {
                binding.tilPassword.error = getString(loginState.passwordError)
            } else {
                binding.tilPassword.error = null
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = binding.tilEmail
        val password = binding.tilPassword


        username.afterTextChanged {
            viewModel.loginDataChanged(
                username.editText?.text.toString(),
                password.editText?.text.toString()
            )
        }
        password.apply {
            afterTextChanged {
                viewModel.loginDataChanged(
                    username.editText?.text.toString(),
                    password.editText?.text.toString()
                )
            }
            binding.login.setOnClickListener {
                viewModel.loginUser(
                    username.editText?.text.toString(),
                    password.editText?.text.toString()
                )
            }

        }

        binding.returnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.forgotPasswordButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_resetPasswordFragment)
        }
        binding.signUpButton.setOnClickListener {
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


    private fun stopLoading() {
        binding.loading.isVisible = false
        binding.login.isEnabled = true
        binding.returnBack.isEnabled = true
        binding.forgotPasswordButton.isEnabled = true
        binding.signUpButton.isEnabled = true
    }

    private fun showLoading() {
        binding.loading.isVisible = true
        binding.login.isEnabled = false
        binding.returnBack.isEnabled = false
        binding.forgotPasswordButton.isEnabled = false
        binding.signUpButton.isEnabled = false
    }
}