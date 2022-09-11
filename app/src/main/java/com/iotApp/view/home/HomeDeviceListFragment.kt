package com.iotApp.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iotApp.R
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_device_list_fragment_to_add_device_fragment)
        }

    }
}