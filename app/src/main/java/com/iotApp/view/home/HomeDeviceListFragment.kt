package com.iotApp.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.iotApp.R
import com.iotApp.databinding.FragmentHomeDeviceListBinding
import com.iotApp.viewmodel.DeviceViewModel
import com.iotApp.viewmodel.ViewModelFactory

class HomeDeviceListFragment : Fragment() {
    private lateinit var viewModel: DeviceViewModel
    private var _binding: FragmentHomeDeviceListBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeDeviceListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this@HomeDeviceListFragment,
            ViewModelFactory()
        )[DeviceViewModel::class.java]
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
//todo: 添加動態生成的設備列表
    }

}