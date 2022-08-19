package com.example.iotapp.ui.familyEdit

import android.content.ClipData.newIntent
import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import androidx.navigation.fragment.NavHostFragment
import com.example.iotapp.MainActivity
import com.example.iotapp.R
import com.example.iotapp.databinding.FragmentFamilyAddBinding


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FamilyAddFragment : Fragment() {

    private var _binding: FragmentFamilyAddBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFamilyAddBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.btnBack.setOnClickListener {
            activity?.finish()
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

