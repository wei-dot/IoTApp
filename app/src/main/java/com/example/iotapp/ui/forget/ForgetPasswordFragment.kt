package com.example.iotapp.ui.forget

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.iotapp.MainActivity
import com.example.iotapp.R
import com.example.iotapp.api.IotApi
import com.example.iotapp.api.ResetPassword
import com.example.iotapp.databinding.FragmentForgetBinding

class ForgetPasswordFragment : Fragment() {
    private var _binding: FragmentForgetBinding? = null
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
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
        binding.btnSend.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            if (email.isEmpty()) {
                binding.inputEmail.error = "信箱不能為空"
            } else {
                binding.loading.isVisible = true
                IotApi().resetPassword(ResetPassword(email), activity,binding)
                Handler(Looper.getMainLooper()).postDelayed({
                    // Your Code
                    startActivity(Intent(activity, MainActivity::class.java))
                    activity?.finish()
                }, 3000)
            }

        }
    }

}