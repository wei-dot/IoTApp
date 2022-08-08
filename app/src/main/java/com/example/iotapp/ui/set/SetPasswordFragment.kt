package com.example.iotapp.ui.set

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.iotapp.MainActivity
import com.example.iotapp.api.IotApi
import com.example.iotapp.api.SetPassword
import com.example.iotapp.databinding.FragmentAccountSetBinding

class SetPasswordFragment : Fragment() {

    private var _binding: FragmentAccountSetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountSetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            activity?.finish()
            startActivity(Intent(this.context, MainActivity::class.java))
        }
        binding.btnSend.setOnClickListener {
            val passwordOld = binding.tilPasswordOld?.editText?.text.toString()
            val passwordNew = binding.tilPasswordNew?.editText?.text.toString()
            val passwordConfirm = binding.tilPasswordConfirm?.editText?.text.toString()
            val setPassword = SetPassword(passwordOld, passwordNew, passwordConfirm)
            binding.loading?.isEnabled = true
            binding.btnSend.isEnabled = false
            binding.btnBack.isEnabled = false
            IotApi().setPassword(setPassword, activity, binding)
            activity?.finish()
            startActivity(Intent(activity, MainActivity::class.java))
        }
    }
}