package com.iotApp.view.family

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import androidx.navigation.fragment.NavHostFragment
import com.iotApp.R

import com.iotApp.databinding.FragmentFamilyMemberAddBinding


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FamilyMemberAddFragment : Fragment() {

    private var _binding: FragmentFamilyMemberAddBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFamilyMemberAddBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.btnBack.setOnClickListener {
            NavHostFragment.findNavController(this@FamilyMemberAddFragment)
                .navigate(R.id.navigation_family_edit)
        }
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

