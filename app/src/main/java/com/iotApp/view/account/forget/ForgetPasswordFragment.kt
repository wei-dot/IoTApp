package com.iotApp.view.account.forget

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.iotApp.api.BaseResponse
import com.iotApp.databinding.FragmentAccountForgetBinding
import com.iotApp.view.account.login.afterTextChanged
import com.iotApp.viewmodel.ForgetPasswordViewModel
import com.iotApp.viewmodel.ViewModelFactory

class ForgetPasswordFragment : Fragment() {
    private lateinit var viewModel: ForgetPasswordViewModel
    private var _binding: FragmentAccountForgetBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountForgetBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this@ForgetPasswordFragment,
            ViewModelFactory()
        )[ForgetPasswordViewModel::class.java]
        viewModel.forgetPasswordResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Success -> {
                    binding.loading.isVisible = false
                    Toast.makeText(requireContext(), "密碼重設信已寄出", Toast.LENGTH_SHORT).show()
                    requireActivity().onBackPressed()
                }
                is BaseResponse.Error -> {
                    binding.loading.isVisible = false
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                }
                is BaseResponse.Loading -> {
                    binding.loading.isVisible = true
                }
                else -> {
                    binding.loading.isVisible = false
                }

            }
        }
        viewModel.loginFormState.observe(viewLifecycleOwner, Observer {
            val loginState = it ?: return@Observer
            binding.forget.isEnabled = loginState.isDataValid
            if (loginState.emailError != null) {
                binding.tilUsername.error = getString(loginState.emailError)
            } else {
                binding.tilUsername.error = null
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.returnBack.setOnClickListener {
            findNavController().popBackStack()
            //            findNavController().navigate(R.id.action_forgetPasswordFragment_to_loginFragment)
        }
        val username = binding.tilUsername

        username.apply {
            afterTextChanged {
                viewModel.forgetPasswordDataChanged(
                    username.editText?.text.toString()
                )
            }
            binding.forget.setOnClickListener {
                viewModel.forgetPassword(username.editText?.text.toString())
            }
        }
    }

}