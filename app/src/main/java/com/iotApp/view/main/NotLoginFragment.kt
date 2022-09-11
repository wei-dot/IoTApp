package com.iotApp.view.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iotApp.databinding.FragmentMainUnloginBinding
import com.iotApp.view.AccountActivity

class NotLoginFragment : Fragment() {
    private var _binding: FragmentMainUnloginBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentMainUnloginBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.btnLogin.setOnClickListener {
            val intent = Intent(activity, AccountActivity::class.java)
            activity?.finish()
            startActivity(intent)
        }

        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}