package com.iotApp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iotApp.databinding.FragmentHomeDeviceListBinding

class HomeDeviceListFragment : Fragment() {
    private var _binding: FragmentHomeDeviceListBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeDeviceListBinding.inflate(inflater, container, false)
        return binding.root
    }
}