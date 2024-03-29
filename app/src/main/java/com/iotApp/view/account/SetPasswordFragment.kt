package com.iotApp.view.account

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.iotApp.api.BaseResponse
import com.iotApp.databinding.FragmentAccountSetBinding
import com.iotApp.repository.SessionManager
import com.iotApp.view.MainActivity
import com.iotApp.viewmodel.AccountViewModel
import com.iotApp.viewmodel.ViewModelFactory
import com.iotApp.viewmodel.afterTextChanged

class SetPasswordFragment : Fragment() {
    private lateinit var viewModel: AccountViewModel
    private var _binding: FragmentAccountSetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountSetBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this@SetPasswordFragment,
            ViewModelFactory()
        )[AccountViewModel::class.java]
        viewModel.setPasswordResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Success -> {
                    binding.loading.isVisible = false
                    Toast.makeText(requireContext(), "設定密碼成功", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(requireContext(), MainActivity::class.java))
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
        viewModel.accountFormState.observe(viewLifecycleOwner, Observer {
            val setPasswordState = it ?: return@Observer
            binding.set.isEnabled = it.isDataValid
            if (setPasswordState.passwordError != null) {
                binding.tilPasswordNew.error = getString(setPasswordState.passwordError)
                binding.tilPasswordConfirm.error = getString(setPasswordState.passwordError)
            } else {
                binding.tilPasswordNew.error = null
                binding.tilPasswordConfirm.error = null

            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.returnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        val oldPassword = binding.tilPasswordOld
        val newPassword = binding.tilPasswordNew
        val confirmPassword = binding.tilPasswordConfirm
        newPassword.apply {
            afterTextChanged {
                viewModel.setPasswordDataChanged(
                    newPassword.editText?.text.toString(),
                    confirmPassword.editText?.text.toString()
                )
            }

        }
        confirmPassword.apply {
            afterTextChanged {
                viewModel.setPasswordDataChanged(
                    newPassword.editText?.text.toString(),
                    confirmPassword.editText?.text.toString()
                )
            }
            binding.set.setOnClickListener {
                viewModel.setPassword(
                    "Token ${SessionManager(requireContext()).fetchAuthToken()}",
                    oldPassword.editText?.text.toString(),
                    newPassword.editText?.text.toString(),
                    confirmPassword.editText?.text.toString()
                )
            }
        }
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        activity?.finish()
                        startActivity(Intent(context, MainActivity::class.java))
                    }
                }
            )
    }
}