package com.example.iotapp.ui.family

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
import androidx.navigation.fragment.NavHostFragment
import com.example.iotapp.FamilyMemberActivity
import com.example.iotapp.api.AlterHome
import com.example.iotapp.api.IotApi
import com.example.iotapp.api.SessionManager
import com.example.iotapp.databinding.FragmentMainFamilyBinding
import kotlin.math.log


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FamilyFragment : Fragment() {

    private var _binding: FragmentMainFamilyBinding? = null

    //testMode *if inside Family
    var hasFamily = true

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
        val FamilyViewModel = ViewModelProvider(this)[FamilyViewModel::class.java]
        val familyMemberList: MutableList<String>? =
            SessionManager(requireActivity()).fetchFamilyMembers()?.toMutableList()
        Log.d("familyMemberList", familyMemberList.toString())
        if (familyMemberList!!.isEmpty()) {
            hasFamily = false
        }
        if (hasFamily) {
            binding.btnAddFamily.isVisible = false
            //test
//            val familyMemberList: List<String> = listOf("島輝", "偷刀", "馬吉亞米", "番仔", "盲胞", "歐巴馬", "勞贖")
            val king = "島輝"
            //test
            val memberList: LinearLayout = binding.familyMemberBoxLinearlayout
            for (i in familyMemberList!!.indices) {
                val memberToAdd = View.inflate(context, com.example.iotapp.R.layout.member, null)
                memberToAdd.id = i
                memberToAdd.findViewById<TextView>(com.example.iotapp.R.id.text_member_name).text =
                    familyMemberList[i]
                memberToAdd.setPadding(38, 0, 38, 0)
                if (familyMemberList[i] == king) {
                    memberToAdd.findViewById<ImageView>(com.example.iotapp.R.id.ic_admin).isVisible =
                        true
                }
                memberList.addView(memberToAdd)
                memberToAdd.setOnClickListener {
                    val popupWindow = PopupWindow(context)
                    val view =
                        layoutInflater.inflate(com.example.iotapp.R.layout.popup_userinfo, null)
                    val popup_username =
                        view.findViewById<TextView>(com.example.iotapp.R.id.popup_username)
                    val popup_user = view.findViewById<TextView>(com.example.iotapp.R.id.popup_user)
                    val kickMember =
                        view.findViewById<ImageButton>(com.example.iotapp.R.id.btn_kickmember)
                    popup_username.text = familyMemberList[i]
                    popup_user.text = familyMemberList[i]
                    if (familyMemberList[i] == king) {
                        kickMember.isVisible = false
                    }
                    kickMember.setOnClickListener {
                        memberList.removeView(memberToAdd)
                        familyMemberList.removeAt(i)
                        val arrayList_familyMemberList = ArrayList(familyMemberList)
                        Log.d("familyMemberListA", arrayList_familyMemberList.toString())
                        val AlterHome = AlterHome(
                            SessionManager(requireActivity()).fetchFamilyName().toString(),
                            arrayList_familyMemberList
                        )

                        Log.d("AlterHome", AlterHome.toString())
                        IotApi.delFamilyMember(activity , binding , SessionManager(requireActivity()),AlterHome)
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
                        popupWindow.animationStyle = com.example.iotapp.R.style.normalAnimationPopup
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
                    layoutInflater.inflate(com.example.iotapp.R.layout.popup_edit_member, null)
                val btn_addmember =
                    view.findViewById<ImageButton>(com.example.iotapp.R.id.popup_edit_member_add_member)
                val btn_editmember =
                    view.findViewById<ImageButton>(com.example.iotapp.R.id.popup_edit_member_edit_member)
                btn_addmember.setOnClickListener {
                    val intent = Intent(context, FamilyMemberActivity::class.java)
                    intent.putExtra("FamilyMemberActivity", "addMember")
                    startActivity(intent)
                    popupWindow.dismiss()
                }
                btn_editmember.setOnClickListener {
                    val intent = Intent(context, FamilyMemberActivity::class.java)
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
                    popupWindow.animationStyle = com.example.iotapp.R.style.normalAnimationPopup
                    popupWindow.setBackgroundDrawable(context?.let { it1 ->
                        ContextCompat.getColor(
                            it1, com.google.android.material.R.color.mtrl_btn_transparent_bg_color
                        )
                    }?.let { it2 -> ColorDrawable(it2) })
                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
                }
            }
        } else {
            binding.btnFamilyEdit.isVisible = false
            binding.btnFamilySetting.isVisible = false
            val textView: TextView = binding.textFamily
            FamilyViewModel.text.observe(viewLifecycleOwner) {
                textView.text = it
            }
            binding.btnAddFamily.setOnClickListener {
                val intent = Intent(context, FamilyMemberActivity::class.java)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

