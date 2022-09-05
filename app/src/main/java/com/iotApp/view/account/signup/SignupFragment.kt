package com.iotApp.view.account.signup

//import com.iotApp.api.UserInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.iotApp.R
import com.iotApp.api.BaseResponse
import com.iotApp.view.account.login.afterTextChanged
import com.iotApp.databinding.FragmentAccountSignupBinding
import com.iotApp.viewmodel.SignupViewModel
import com.iotApp.viewmodel.ViewModelFactory

class SignupFragment : Fragment() {
    private lateinit var viewModel: SignupViewModel
    private var _binging: FragmentAccountSignupBinding? = null
    private val binding get() = _binging!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binging = FragmentAccountSignupBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this@SignupFragment,
           ViewModelFactory()
        )[SignupViewModel::class.java]
        viewModel.signupFormState.observe(viewLifecycleOwner, Observer {
            val signupState = it ?: return@Observer
            binding.signup.isEnabled = it.isDataValid
            if (signupState.usernameError != null) {
                binding.tilUsername.error = getString(signupState.usernameError)
            } else {
                binding.tilUsername.error = null
            }
            if (signupState.passwordError != null) {
                binding.tilPassword.error = getString(signupState.passwordError)
                binding.tilPasswordConfirm.error = getString(signupState.passwordError)
            } else {
                binding.tilPassword.error = null
                binding.tilPasswordConfirm.error = null
            }
            if (signupState.emailError != null) {
                binding.tilEmail.error = getString(signupState.emailError)
            } else {
                binding.tilEmail.error = null
            }
        })
        viewModel.signupResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Success -> {
                    setFragmentResult(
                        "requestKey",
                        bundleOf("bundleKey" to binding.tilEmail.editText?.text.toString())
                    )
                    Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_signupFragment_to_resendFragment)
                }
                is BaseResponse.Error -> {
                    stopLoading()
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                }
                is BaseResponse.Loading -> {
                    showLoading()
                }
                else -> {
                    stopLoading()
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = binding.tilUsername
        val password = binding.tilPassword
        val passwordConfirm = binding.tilPasswordConfirm
        val email = binding.tilEmail

        username.afterTextChanged {
            viewModel.signupDataChanged(
                username = username.editText?.text.toString(),
                password = password.editText?.text.toString(),
                passwordConfirm = passwordConfirm.editText?.text.toString(),
                email = email.editText?.text.toString()
            )
        }
        password.afterTextChanged {
            viewModel.signupDataChanged(
                username = username.editText?.text.toString(),
                password = password.editText?.text.toString(),
                passwordConfirm = passwordConfirm.editText?.text.toString(),
                email = email.editText?.text.toString()
            )
        }
        passwordConfirm.afterTextChanged {
            viewModel.signupDataChanged(
                username = username.editText?.text.toString(),
                password = password.editText?.text.toString(),
                passwordConfirm = passwordConfirm.editText?.text.toString(),
                email = email.editText?.text.toString()
            )
        }
        email.apply {
            afterTextChanged {
                viewModel.signupDataChanged(
                    username = username.editText?.text.toString(),
                    password = password.editText?.text.toString(),
                    passwordConfirm = passwordConfirm.editText?.text.toString(),
                    email = email.editText?.text.toString()
                )
            }
            binding.signup.setOnClickListener {
                viewModel.signup(
                    username = username.editText?.text.toString(),
                    password = password.editText?.text.toString(),
                    passwordConfirm = passwordConfirm.editText?.text.toString(),
                    email = email.editText?.text.toString()
                )
            }
        }



        binding.returnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    private fun stopLoading() {
        binding.loading.isVisible = false
        binding.returnBack.isEnabled = true
    }

    private fun showLoading() {
        binding.loading.isVisible = true
        binding.returnBack.isEnabled = false
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binging = null

    }

}