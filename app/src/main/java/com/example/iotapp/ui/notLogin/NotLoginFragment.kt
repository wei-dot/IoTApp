package com.example.iotapp.ui.notLogin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.iotapp.AccountActivity
import com.example.iotapp.databinding.FragmentMainUnloginBinding


class NotLoginFragment : Fragment() {
    private var _binding: FragmentMainUnloginBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainUnloginBinding.inflate(inflater, container, false)
        binding.btnLogin?.setOnClickListener {
            startActivity(Intent(activity, AccountActivity::class.java))
            activity?.finish()
        }


        return binding.root
    }
}