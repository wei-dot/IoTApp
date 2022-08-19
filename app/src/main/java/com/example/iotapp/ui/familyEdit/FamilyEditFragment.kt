package com.example.iotapp.ui.familyEdit

import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.iotapp.MainActivity
import com.example.iotapp.R

import com.example.iotapp.databinding.FragmentFamilyEditBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FamilyEditFragment : Fragment() {

    private var _binding:FragmentFamilyEditBinding ?= null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFamilyEditBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.btnBack.setOnClickListener {
            activity?.finish()
        }
        binding.btnExitFamily.setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
        }
        binding.btnAddMember.setOnClickListener {
            findNavController().navigate(R.id.navigation_family_member_add)
        }
        return root
    }
    private fun backgroundAlpha(f: Float) {
        val lp = activity?.window?.attributes
        lp?.alpha = f
        activity?.window?.attributes = lp
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

