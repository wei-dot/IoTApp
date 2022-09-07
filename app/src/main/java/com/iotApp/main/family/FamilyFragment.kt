package com.iotApp.main.family

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.iotApp.ChatRoomDemo
import com.iotApp.FamilyActivity
import com.iotApp.api.AlterHome
import com.iotApp.api.IotApi
import com.iotApp.api.SessionManager
import com.iotApp.databinding.FragmentMainFamilyBinding


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FamilyFragment : Fragment() {

    private var _binding: FragmentMainFamilyBinding? = null

    //testMode *if inside Family
    private var hasFamily = true

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainFamilyBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val familyViewModel = ViewModelProvider(this)[FamilyViewModel::class.java]
        val familyMemberList: MutableList<String>? =
            SessionManager(requireActivity()).fetchFamilyMembers()?.toMutableList()
        Log.d("familyMemberList", familyMemberList.toString())
        if (familyMemberList!!.isEmpty()) {
            hasFamily = false
        }
        if (hasFamily) {
            binding.btnAddFamily.isVisible = false
            IotApi.getMyOwnFamily(activity, SessionManager(requireActivity()))
            val memberList: LinearLayout = binding.familyMemberBoxLinearlayout
            for (i in familyMemberList.indices) {
                val memberToAdd = View.inflate(context, com.iotApp.R.layout.item_member, null)
                memberToAdd.id = i
                memberToAdd.findViewById<TextView>(com.iotApp.R.id.text_member_name).text =
                    familyMemberList[i]
                memberToAdd.setPadding(38, 0, 38, 0)
                memberList.addView(memberToAdd)
                memberToAdd.setOnClickListener {
                    val popupWindow = PopupWindow(context)
                    val view =
                        layoutInflater.inflate(com.iotApp.R.layout.popup_userinfo, null)
                    val popupUsername =
                        view.findViewById<TextView>(com.iotApp.R.id.popup_username)
                    val popupUser = view.findViewById<TextView>(com.iotApp.R.id.popup_user)
                    val kickMember =
                        view.findViewById<MaterialButton>(com.iotApp.R.id.btn_kickmember)
                    popupUsername.text = familyMemberList[i]
                    popupUser.text = familyMemberList[i]
                    if (!SessionManager(requireActivity()).fetchMyOwnFamily()!!.contains(SessionManager(requireActivity()).fetchFamilyId())) {
                        kickMember.isVisible = false
                    }
                    else {
                        kickMember.isVisible = familyMemberList[i] != SessionManager(requireActivity()).fetchUserInfo()?.username
                    }
                    kickMember.setOnClickListener {
                        binding.loading.isVisible = true
                        memberList.removeView(memberToAdd)
                        familyMemberList.removeAt(i)
                        val familyMemberListView = ArrayList(familyMemberList)
                        Log.d("familyMemberListA", familyMemberListView.toString())
                        val alterHome = AlterHome(
                            SessionManager(requireActivity()).fetchFamilyName().toString(),
                            familyMemberListView
                        )
                        Log.d("AlterHome", alterHome.toString())
                        IotApi.delFamilyMember(activity , binding , SessionManager(requireActivity()),alterHome)
                        popupWindow.dismiss()
                    }
                    popupWindow.setOnDismissListener {
                        backgroundAlpha(1f)
                    }
                    if (popupWindow.isShowing) {
                        popupWindow.dismiss()
                    } else {
                        backgroundAlpha(0.8f)
                        popupWindow.contentView = view
                        popupWindow.width = LinearLayout.LayoutParams.WRAP_CONTENT
                        popupWindow.height = LinearLayout.LayoutParams.WRAP_CONTENT
                        popupWindow.isFocusable = true
                        popupWindow.isOutsideTouchable = true
                        popupWindow.animationStyle = com.iotApp.R.style.normalAnimationPopup
                        popupWindow.setBackgroundDrawable(context?.let { it1 ->
                            ContextCompat.getColor(
                                it1,
                                com.google.android.material.R.color.mtrl_btn_transparent_bg_color
                            )
                        }?.let { it2 -> ColorDrawable(it2) })
                        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
                    }
                }
            }
            binding.btnFamilyEdit.setOnClickListener {
                val popupWindow = PopupWindow(context)
                val view =
                    layoutInflater.inflate(com.iotApp.R.layout.popup_edit_member, null)
                val btnAddMember =
                    view.findViewById<ImageButton>(com.iotApp.R.id.popup_edit_member_add_member)
                val btnEditMember =
                    view.findViewById<ImageButton>(com.iotApp.R.id.popup_edit_member_edit_member)
                btnAddMember.setOnClickListener {
                    val intent = Intent(context, FamilyActivity::class.java)
                    intent.putExtra("FamilyMemberActivity", "addMember")
                    startActivity(intent)
                    popupWindow.dismiss()
                }
                btnEditMember.setOnClickListener {
                    val intent = Intent(context, FamilyActivity::class.java)
                    intent.putExtra("FamilyMemberActivity", "editMember")
                    startActivity(intent)
                    popupWindow.dismiss()
                }
                popupWindow.setOnDismissListener {
                    backgroundAlpha(1f)
                }
                if (popupWindow.isShowing) {
                    popupWindow.dismiss()
                } else {
                    backgroundAlpha(0.8f)
                    popupWindow.contentView = view
                    popupWindow.width = LinearLayout.LayoutParams.WRAP_CONTENT
                    popupWindow.height = LinearLayout.LayoutParams.WRAP_CONTENT
                    popupWindow.isFocusable = true
                    popupWindow.isOutsideTouchable = true
                    popupWindow.animationStyle = com.iotApp.R.style.normalAnimationPopup
                    popupWindow.setBackgroundDrawable(context?.let { it1 ->
                        ContextCompat.getColor(
                            it1, com.google.android.material.R.color.mtrl_btn_transparent_bg_color
                        )
                    }?.let { it2 -> ColorDrawable(it2) })
                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
                }
            }
            binding.btnFamilySetting.setOnClickListener {
//                Toast.makeText(context, SessionManager(requireActivity()).fetchFamilyId().toString(), Toast.LENGTH_SHORT).show()
//                IotApi.getMyOwnFamily(activity,SessionManager(requireActivity()))
                activity?.startActivity(Intent(activity, ChatRoomDemo::class.java))
            }
        } else {
            binding.btnFamilyEdit.isVisible = false
            binding.btnFamilySetting.isVisible = false
            val textView: TextView = binding.textFamily
            familyViewModel.text.observe(viewLifecycleOwner) {
                textView.text = it
            }
            binding.btnAddFamily.setOnClickListener {
                val intent = Intent(context, FamilyActivity::class.java)
                intent.putExtra("FamilyMemberActivity", "addFamily")
                startActivity(intent)
            }
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

