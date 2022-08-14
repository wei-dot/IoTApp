package com.example.iotapp.ui.family
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.iotapp.R
import com.example.iotapp.databinding.FragmentFamilyNotinBinding





/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

class FamilyFragment : Fragment() {

    //testMode *if inside Family
    var testMode = true

    private var _binding: FragmentFamilyNotinBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (testMode){
            findNavController().navigate(R.id.action_navigation_family_to_navigation_family_in)
        }
        val familyViewModel = ViewModelProvider(this)[FamilyViewModel::class.java]
        _binding = FragmentFamilyNotinBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val textView: TextView = binding.textFamily
        familyViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        binding.btnAddFamily?.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_family_to_navigation_family_in)
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

