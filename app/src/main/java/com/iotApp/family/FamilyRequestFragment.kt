package com.iotApp.family

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.iotApp.R
import com.iotApp.api.IotApi
import com.iotApp.databinding.FragmentFamilyRequestBinding
import com.iotApp.model.AlterHome
import com.iotApp.repository.SessionManager


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FamilyRequestFragment : Fragment() {

    private var _binding: FragmentFamilyRequestBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFamilyRequestBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.btnBack.setOnClickListener {
            NavHostFragment.findNavController(this@FamilyRequestFragment)
                .navigate(R.id.navigation_family_edit)
        }

        binding.btnAccept.setOnClickListener {
            var members: MutableList<String> =
                SessionManager(requireContext()).fetchFamilyMembers()!!.toMutableList()
            members.add(SessionManager(requireContext()).fetchRequestUserName()!!)
            val arrayList = ArrayList(members)
            Log.d("FamilyRequest", "members: $arrayList")

            val alterHome = AlterHome(
                SessionManager(requireContext()).fetchFamilyName()!!,
                arrayList
            )

            IotApi.addFamilyMember(activity, binding, SessionManager(requireContext()), alterHome)
        }


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

