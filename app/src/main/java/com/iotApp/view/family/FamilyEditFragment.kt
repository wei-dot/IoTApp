package com.iotApp.view.family

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iotApp.R
import com.iotApp.api.AlterHome
import com.iotApp.api.IotApi
import com.iotApp.api.SessionManager

import com.iotApp.databinding.FragmentFamilyEditBinding

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
            IotApi.updateFamilyMemberByFamilyID(SessionManager(requireActivity()))
            if (SessionManager(requireActivity()).fetchMyOwnFamily()!!.contains(SessionManager(requireActivity()).fetchFamilyId().toString())) {
                //表示為管理員，執行刪除整個家庭動作
                IotApi.deleteFamily(activity,binding,SessionManager(requireActivity()))
            }
            else{
                //表示為成員，僅執行調整成員動作
                val newMemberList : ArrayList<String> = ArrayList()
//                SessionManager(requireActivity()).fetchFamilyMembers()?.iterator()?.forEach { member ->
//                    if (member != SessionManager(requireActivity()).fetchUserInfo()?.username) {
//                        newMemberList.add(member)
//                    }
//                }
                val alterHome = AlterHome(SessionManager(requireActivity()).fetchFamilyName().toString(), newMemberList)
                IotApi.exitFamily(activity, binding , SessionManager(requireActivity()) , alterHome)
            }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

