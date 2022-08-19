package com.example.iotapp.ui.mode

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.fragment.app.Fragment
import com.example.iotapp.databinding.FragmentMainMode3NamingBinding

class ModeSetNamingFragment : Fragment() {
    private var _binding: FragmentMainMode3NamingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: android.os.Bundle?
    ): View {
        _binding = FragmentMainMode3NamingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

}