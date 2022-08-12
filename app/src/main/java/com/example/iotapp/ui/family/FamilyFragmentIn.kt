package com.example.iotapp.ui.family
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.iotapp.databinding.FragmentFamilyInBinding



/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FamilyFragmentIn : Fragment() {

    private var _binding: FragmentFamilyInBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val familyViewModel = ViewModelProvider(this)[FamilyViewModel::class.java]
        _binding = FragmentFamilyInBinding.inflate(inflater, container, false)

        val root: View = binding.root


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