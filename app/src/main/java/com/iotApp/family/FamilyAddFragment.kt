package com.iotApp.family

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iotApp.R
import com.iotApp.databinding.FragmentFamilyAddBinding


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
        binding.createFamily.setOnClickListener {
            findNavController().navigate(R.id.navigation_create_family)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

