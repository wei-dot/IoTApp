package com.example.iotapp.ui.forget

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.iotapp.R
import com.example.iotapp.databinding.FragmentForgetBinding
class ForgetPasswordFragment:Fragment() {
    private var _binding: FragmentForgetBinding ?= null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.loginFragment)
        }
        binding.btnSend?.setOnClickListener{
            findNavController().navigate(R.id.action_forgetPasswordFragment_to_resetPasswordFragment)
        }
    }

}