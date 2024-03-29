package com.iotApp.view.main.family

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.iotApp.api.IotApi
import com.iotApp.databinding.FragmentMainFamilyBinding
import com.iotApp.model.AlterHome
import com.iotApp.repository.SessionManager
import com.iotApp.view.FamilyActivity


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
        val familyMemberList: MutableList<String>? =
            SessionManager(requireActivity()).fetchFamilyMembers()?.toMutableList()
        Log.d("familyMemberList", familyMemberList.toString())
        if (familyMemberList!!.isEmpty()) {
            hasFamily = false
        }
        if (hasFamily) {
            binding.btnAddFamily.isVisible = false
            IotApi.getMyOwnFamily(SessionManager(requireActivity()))
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
                    val kickMember =
                        view.findViewById<MaterialButton>(com.iotApp.R.id.btn_kickmember)
                    popupUsername.text = familyMemberList[i]
                    if (!SessionManager(requireActivity()).fetchMyOwnFamily()!!
                            .contains(SessionManager(requireActivity()).fetchFamilyId())
                    ) {
                        kickMember.isVisible = false
                    } else {
                        kickMember.isVisible =
                            familyMemberList[i] != SessionManager(requireActivity()).fetchUserInfo()?.username
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
                        IotApi.delFamilyMember(
                            activity,
                            binding,
                            SessionManager(requireActivity()),
                            alterHome
                        )
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
                val btnExitFamily =
                    view.findViewById<ImageButton>(com.iotApp.R.id.popup_edit_member_exit)
                btnAddMember.setOnClickListener {
                    val intent = Intent(context, FamilyActivity::class.java)
                    intent.putExtra("FamilyMemberActivity", "addMember")
                    startActivity(intent)
                    popupWindow.dismiss()
                }
                btnExitFamily.setOnClickListener {
                    IotApi.updateFamilyMemberByFamilyID(SessionManager(requireActivity()))
                    if (SessionManager(requireActivity()).fetchMyOwnFamily()!!
                            .contains(SessionManager(requireActivity()).fetchFamilyId().toString())
                    ) {
                        //表示為管理員，執行刪除整個家庭動作
                        IotApi.deleteFamily(activity, SessionManager(requireActivity()))
                    } else {
                        //表示為成員，僅執行調整成員動作
                        val newMemberList: ArrayList<String> = ArrayList()
                        SessionManager(requireActivity()).fetchFamilyMembers()?.iterator()
                            ?.forEach { member ->
                                if (member != SessionManager(requireActivity()).fetchUserInfo()?.username) {
                                    newMemberList.add(member)
                                }
                            }
                        val alterHome = AlterHome(
                            SessionManager(requireActivity()).fetchFamilyName().toString(),
                            newMemberList
                        )
                        IotApi.exitFamily(activity, SessionManager(requireActivity()), alterHome)
                    }
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
            binding.btnLoadChatroom.setOnClickListener {
                binding.btnLoadChatroom.isVisible = false
                binding.chatRoomLayout.isVisible = true
                ChatRoomEmbed().chatRoomEmbed(requireActivity(), binding)
            }
        } else {
            binding.btnFamilyEdit.isVisible = false
            binding.btnLoadChatroom.isVisible = false
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

