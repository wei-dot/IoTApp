package com.example.iotapp.ui.family

import android.R
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Layout
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
import com.example.iotapp.databinding.FragmentMainFamilyBinding


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FamilyFragment : Fragment() {

    private var _binding: FragmentMainFamilyBinding? = null
    //testMode *if inside Family
    var testMode = true

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainFamilyBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val FamilyViewModel = ViewModelProvider(this).get(FamilyViewModel::class.java)
        if (testMode) {
            binding.btnAddFamily.isVisible = false
            //test
            val member_num: List<String> = listOf("島輝", "偷刀", "馬吉亞米", "番仔", "盲胞", "歐巴馬", "勞贖")
            val king: String = "島輝"
            //test
            val memberList: LinearLayout = binding.familyMemberBoxLinearlayout
            for (i in member_num.indices) {
                val memberToAdd = View.inflate(context, com.example.iotapp.R.layout.member, null)
                memberToAdd.id = i
                memberToAdd.findViewById<TextView>(com.example.iotapp.R.id.text_member_name).text = member_num[i]
                memberToAdd.setPadding(38, 0, 38, 0)
                if (member_num[i] == king) {
                    memberToAdd.findViewById<ImageView>(com.example.iotapp.R.id.ic_admin).isVisible = true
                }
                memberList.addView(memberToAdd)
                memberToAdd.setOnClickListener {
                    val popupWindow = PopupWindow(context)
                    val view = layoutInflater.inflate(com.example.iotapp.R.layout.popup_userinfo, null)
                    val popup_username = view.findViewById<TextView>(com.example.iotapp.R.id.popup_username)
                    val popup_user = view.findViewById<TextView>(com.example.iotapp.R.id.popup_user)
                    val kickMember = view.findViewById<ImageButton>(com.example.iotapp.R.id.btn_kickmember)
                    popup_username.text = member_num[i]
                    popup_user.text = member_num[i]
                    if (member_num[i] == king) {
                        kickMember.isVisible = false
                    }
                    kickMember.setOnClickListener{
                        memberList.removeView(memberToAdd)
                        member_num.drop(i)
                        popupWindow.dismiss()
                    }
                    popupWindow.setOnDismissListener {
                        backgroundAlpha(1f)
                    }
                    if (popupWindow.isShowing) {
                        popupWindow.dismiss()
                    }
                    else{
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
            }
            binding.btnFamilyEdit.setOnClickListener {
                val popupWindow = PopupWindow(context)
                val view = layoutInflater.inflate(com.example.iotapp.R.layout.popup_edit_member, null)
                val btn_addmember = view.findViewById<ImageButton>(com.example.iotapp.R.id.popup_edit_member_add_member)
                val btn_editmember = view.findViewById<ImageButton>(com.example.iotapp.R.id.popup_edit_member_edit_member)

                popupWindow.setOnDismissListener {
                    backgroundAlpha(1f)
                }
                if (popupWindow.isShowing) {
                    popupWindow.dismiss()
                }
                else{
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
        }
        else{
            binding.btnFamilyEdit.isVisible = false
            binding.btnFamilySetting.isVisible = false
            val textView: TextView = binding.textFamily
            FamilyViewModel.text.observe(viewLifecycleOwner) {
                textView.text = it
            }
            binding.btnAddFamily.setOnClickListener{
//                popupByClick(R.layout.popup_add_member)
                testMode = true
                refreshFragment()
            }
        }
        return root
    }
    private fun refreshFragment() {
        // This method refreshes the fragment
        NavHostFragment.findNavController(this).navigate(com.example.iotapp.R.id.navigation_family)
    }

    private fun popupByClick(layoutID: Int){
        val popupWindow = PopupWindow(context)
        val view = layoutInflater.inflate(layoutID, null)
        popupWindow.setOnDismissListener {
            backgroundAlpha(1f)
        }
        if (popupWindow.isShowing) {
            popupWindow.dismiss()
        }
        else{
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