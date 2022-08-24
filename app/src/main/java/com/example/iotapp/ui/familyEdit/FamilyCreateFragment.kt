package com.example.iotapp.ui.familyEdit
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.iotapp.R
import com.example.iotapp.api.CreateHome
import com.example.iotapp.api.IotApi
import com.example.iotapp.api.SessionManager
import com.example.iotapp.databinding.FragmentFamilyCreateBinding


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
            findNavController().navigate(R.id.navigation_family_add)
        }
        binding.btnSendFamilyName.setOnClickListener {
            binding.loading.isVisible = true
            if (binding.tilFamilyName.editText?.text!!.isNotEmpty() && binding.tilFamilyName.editText!!.text.isNotBlank()) {
                val createHome = CreateHome(binding.tilFamilyName.editText?.text!!.toString(), arrayListOf(SessionManager(requireContext()).fetchUserName()) as ArrayList<String>)
                IotApi.createHome(createHome, activity,binding, SessionManager(requireContext()))
            } else {
                Toast.makeText(context, "家庭名稱欄位不得為空", Toast.LENGTH_LONG).show()
            }
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

