package com.iotApp.view.account.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.iotApp.view.MainActivity
import com.iotApp.R
import com.iotApp.SessionManager
import com.iotApp.api.BaseResponse
import com.iotApp.databinding.FragmentAccountLoginBinding
import com.iotApp.viewmodel.LoginViewModel
import com.iotApp.viewmodel.ViewModelFactory

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
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
        )[LoginViewModel::class.java]
        viewModel.loginResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }
                is BaseResponse.Success -> {
                    stopLoading()
                    it.data?.let { it1 ->
                        SessionManager.saveAuthToken(
                            requireContext(),
                            it1.authToken
                        )
                    }
                    Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(requireContext(), MainActivity::class.java))
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
        viewModel.loginFormState.observe(viewLifecycleOwner, Observer {
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

fun TextInputLayout.afterTextChanged(afterTextChanged: (String) -> Unit) {
    editText?.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
}