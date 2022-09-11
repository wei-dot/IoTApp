package com.iotApp.family

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iotApp.R
import com.iotApp.api.IotApi
import com.iotApp.databinding.FragmentFamilyCreateBinding
import com.iotApp.model.CreateHome
import com.iotApp.repository.SessionManager


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FamilyCreateFragment : Fragment() {

    private var _binding: FragmentFamilyCreateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFamilyCreateBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_create_family_to_navigation_family_add)
        }
        binding.btnSendFamilyName.setOnClickListener {
            binding.loading.isVisible = true
            if (binding.tilFamilyName.editText?.text!!.isNotEmpty() && binding.tilFamilyName.editText!!.text.isNotBlank()) {
                val createHome = CreateHome(
                    binding.tilFamilyName.editText?.text!!.toString(),
                    arrayListOf(
                        SessionManager(requireContext()).fetchUserInfo()!!.username
                    )
                )
                IotApi.createHome(createHome, activity, binding, SessionManager(requireContext()))
            } else {
                Toast.makeText(context, "家庭名稱欄位不得為空", Toast.LENGTH_LONG).show()
            }
        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        findNavController().navigate(R.id.action_navigation_create_family_to_navigation_family_add)
                    }
                }
            )

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

