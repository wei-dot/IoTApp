package com.iotApp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.iotApp.R
import com.iotApp.databinding.FragmentHomeAddDeviceBinding

class HomeAddDeviceFragment : Fragment() {
    private var _binding: FragmentHomeAddDeviceBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeAddDeviceBinding.inflate(inflater, container, false)
        binding.returnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        val items = listOf("感測器", "家用電器")
        val adapter = ArrayAdapter(requireContext(), R.layout.device_list_item, items)
        (binding.menu.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        return binding.root
    }
}