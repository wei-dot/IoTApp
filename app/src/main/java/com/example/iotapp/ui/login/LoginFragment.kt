package com.example.iotapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.iotapp.R
import com.example.iotapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binging: FragmentLoginBinding? = null
    private val binding get() = _binging!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binging = FragmentLoginBinding.inflate(inflater, container, false)
        val root = binding.root
        return root
    }
}