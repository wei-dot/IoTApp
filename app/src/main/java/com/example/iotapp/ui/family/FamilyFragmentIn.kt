package com.example.iotapp.ui.family

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.core.view.marginLeft
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.iotapp.R
import com.example.iotapp.databinding.FragmentFamilyInBinding
import android.widget.PopupWindow
import androidx.core.content.ContextCompat


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

        //test
        val member_num : List<String> = listOf("島輝","偷刀","馬吉亞米","番仔","盲胞","歐巴馬")
        val king : String = "島輝"
        //test

        val root: View = binding.root
        val memberList : LinearLayout? = binding.familyMemberBoxLinearlayout
        for(i in 0 until member_num.size){
            val memberToAdd = View.inflate(context, R.layout.member, null)
            memberToAdd.id = i
            memberToAdd.findViewById<TextView>(R.id.text_member_name).text = member_num[i]
            memberToAdd.setPadding(38,0,38,0)
            if (member_num[i] == king){
                memberToAdd.findViewById<ImageView>(R.id.ic_admin).isVisible = true
            }
            memberList?.addView(memberToAdd)
            memberToAdd.setOnClickListener{
                Toast.makeText(context, member_num[i], Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnFamilyEdit.setOnClickListener{
            val popupWindow = PopupWindow(context)
            val view = layoutInflater.inflate(R.layout.popup_edit_member, null)
            popupWindow.contentView = view
            popupWindow.width = LinearLayout.LayoutParams.WRAP_CONTENT
            popupWindow.height = LinearLayout.LayoutParams.WRAP_CONTENT
            popupWindow.isFocusable = true
            popupWindow.isOutsideTouchable = true
            popupWindow.animationStyle = R.style.normalAnimationPopup
            popupWindow.setBackgroundDrawable(context?.let { it1 ->
                ContextCompat.getColor(
                    it1, com.google.android.material.R.color.mtrl_btn_transparent_bg_color)
            }?.let { it2 -> ColorDrawable(it2) })
            popupWindow.showAtLocation(root, Gravity.CENTER, 0, 0)
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