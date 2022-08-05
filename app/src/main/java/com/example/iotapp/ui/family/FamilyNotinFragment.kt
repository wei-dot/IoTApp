package com.example.iotapp.ui.family

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
<<<<<<< HEAD:app/src/main/java/com/example/iotapp/ui/family/FamilyNotinFragment.kt
import com.example.iotapp.databinding.FragmentFamilyNotinBinding
=======
import com.example.iotapp.databinding.FragmentMainFamilyBinding
>>>>>>> 7f8ee60fd16e313e169e92aa5d812400530943b4:app/src/main/java/com/example/iotapp/ui/family/FamilyFragment.kt

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FamilyNotinFragment : Fragment() {

<<<<<<< HEAD:app/src/main/java/com/example/iotapp/ui/family/FamilyNotinFragment.kt
    private var _binding: FragmentFamilyNotinBinding? = null
=======
    private var _binding: FragmentMainFamilyBinding? = null
>>>>>>> 7f8ee60fd16e313e169e92aa5d812400530943b4:app/src/main/java/com/example/iotapp/ui/family/FamilyFragment.kt

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val familyViewModel = ViewModelProvider(this)[FamilyViewModel::class.java]
<<<<<<< HEAD:app/src/main/java/com/example/iotapp/ui/family/FamilyNotinFragment.kt
        _binding = FragmentFamilyNotinBinding.inflate(inflater, container, false)
=======
        _binding = FragmentMainFamilyBinding.inflate(inflater, container, false)
>>>>>>> 7f8ee60fd16e313e169e92aa5d812400530943b4:app/src/main/java/com/example/iotapp/ui/family/FamilyFragment.kt
        val root: View = binding.root
        val textView: TextView = binding.textFamily
        familyViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
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