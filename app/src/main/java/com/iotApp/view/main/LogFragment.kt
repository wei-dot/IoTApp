package com.iotApp.view.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iotApp.adapter.LogAdapter
import com.iotApp.api.BaseResponse
import com.iotApp.databinding.FragmentMainLogBinding
import com.iotApp.model.DeviceData
import com.iotApp.repository.SessionManager
import com.iotApp.view.HomeActivity
import com.iotApp.viewmodel.DeviceViewModel
import com.iotApp.viewmodel.ViewModelFactory
import java.util.*

class LogFragment : Fragment() {
    private var _binding: FragmentMainLogBinding? = null
    private lateinit var viewModel: DeviceViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LogAdapter
    private lateinit var mData: ArrayList<DeviceData>
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainLogBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModel =
            ViewModelProvider(this@LogFragment, ViewModelFactory())[DeviceViewModel::class.java]
        SessionManager(this.requireContext()).fetchAuthToken()
            ?.let {
                viewModel.getDevice("Token $it")
            }
        SessionManager(requireContext()).fetchAuthToken()
            ?.let { viewModel.getDeviceData("Token $it") }
//        binding.logBackground.visibility = View.INVISIBLE
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.deviceDataList.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {
                }
                is BaseResponse.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                    val data = it.data

                    mData = data!!
                    adapter = LogAdapter(mData)
                    recyclerView.adapter = adapter
                }
                is BaseResponse.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                }
                else -> {
                }

            }
        }
        viewModel.deviceList.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {
                }
                is BaseResponse.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                    val data = it.data
                    if (data!!.isEmpty()) {
                        binding.logBackground.visibility = View.INVISIBLE
                    } else {
                        binding.logBackground.visibility = View.VISIBLE
                    }
                }
                is BaseResponse.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                }
                else -> {
                }
            }
        }


        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (activity != null) {
                    activity?.runOnUiThread {
                        SessionManager(requireContext()).fetchAuthToken()
                            ?.let { viewModel.getDeviceData("Token $it") }
                    }
                }
            }
        }, 0, 10000)
        _binding?.add?.setOnClickListener {
            val intent = Intent(requireActivity(), HomeActivity::class.java)
            intent.putExtra("Home", "AddDevice")
            startActivity(intent)
        }
        binding.swipeRefresh.setOnRefreshListener {
            SessionManager(requireContext()).fetchAuthToken()
                ?.let { viewModel.getDeviceData("Token $it") }
            SessionManager(this.requireContext()).fetchAuthToken()
                ?.let { viewModel.getDevice("Token $it") }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}