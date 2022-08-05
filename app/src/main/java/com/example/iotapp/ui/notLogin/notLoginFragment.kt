package com.example.iotapp.ui.notLogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.iotapp.R
import com.example.iotapp.databinding.FragmentNotLoginBinding

class notLoginFragment {
    private val _binding: FragmentNotLoginBinding? = null
    class notLoginFragment : Fragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_not_login, container, false)
        }
    }
}