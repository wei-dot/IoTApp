package com.iotApp.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iotApp.R
import com.iotApp.adapter.DeviceListAdapter
import com.iotApp.api.BaseResponse
import com.iotApp.databinding.FragmentHomeDeviceListBinding
import com.iotApp.model.Device
import com.iotApp.repository.SessionManager
import com.iotApp.viewmodel.DeviceViewModel
import com.iotApp.viewmodel.ViewModelFactory


class HomeDeviceListFragment : Fragment() {
    private var _binding: FragmentHomeDeviceListBinding? = null
    private lateinit var viewModel: DeviceViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DeviceListAdapter
    private lateinit var mData: ArrayList<Device>
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
        SessionManager(this.requireContext()).fetchAuthToken()
            ?.let {
                viewModel.getDevice("Token $it")
            }
        recyclerView = binding.recyclerView

        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.deviceList.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {
                }
                is BaseResponse.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                    mData = it.data!!
                    adapter = DeviceListAdapter(mData)
                    recyclerView.adapter = adapter
                }
                is BaseResponse.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                }
                else -> {
                }
            }
        }

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
        binding.swipeRefresh.setOnRefreshListener {
            SessionManager(this.requireContext()).fetchAuthToken()
                ?.let {
                    viewModel.getDevice("Token $it")
                }
        }
    }

}