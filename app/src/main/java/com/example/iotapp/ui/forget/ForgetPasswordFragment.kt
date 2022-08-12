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
import com.example.iotapp.api.SendEmail
import com.example.iotapp.databinding.FragmentAccountForgetBinding

class ForgetPasswordFragment : Fragment() {
    private var _binding: FragmentAccountForgetBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountForgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_forgetPasswordFragment_to_loginFragment)
        }
        binding.btnSend.setOnClickListener {
            val email = binding.tilUsername?.editText?.text.toString()
            if (email.isEmpty()) {
                binding.tilUsername?.editText?.error  = "信箱不能為空"
            } else {
                binding.loading.isVisible = true
                IotApi().resetPassword(SendEmail(email), activity,binding)
                Handler(Looper.getMainLooper()).postDelayed({
                    // Your Code
                    activity?.finish()
                    startActivity(Intent(activity, MainActivity::class.java))
                }, 3000)
            }

        }
    }

}