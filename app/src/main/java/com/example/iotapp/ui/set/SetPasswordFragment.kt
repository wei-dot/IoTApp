package com.example.iotapp.ui.set

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.iotapp.api.IotApi
import com.example.iotapp.api.SetPassword
import com.example.iotapp.databinding.FragmentAccountSetBinding

class SetPasswordFragment : Fragment() {

    private var _binging: FragmentAccountSetBinding? = null
    private val binding get() = _binging!!

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binging = FragmentAccountSetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            activity?.finish()
        }
        binding.btnSend.setOnClickListener {
            val passwordOld = binding.etPasswordOld?.text.toString()
            val passwordNew = binding.etPasswordNew.text.toString()
            val passwordConfirm = binding.etPasswordConfirm?.text.toString()
            val setPassword = SetPassword(passwordOld, passwordNew, passwordConfirm)
            binding.loading?.isEnabled = true
            binding.btnSend.isEnabled = false
            binding.btnBack.isEnabled = false
            IotApi().setPassword(setPassword, activity, binding)
            activity?.finish()
        }
    }
}