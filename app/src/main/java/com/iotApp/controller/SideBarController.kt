package com.iotApp.controller
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.iotApp.MainActivity
import com.iotApp.R
import com.iotApp.api.Home
import com.iotApp.api.SessionManager

class SideBarController {
    fun sideBar(activity : Activity , binding : com.iotApp.databinding.UserProfileBinding,sessionManager: SessionManager , familyList : List<String>, response : ArrayList<Home>) {
        val myFamilyList: LinearLayout = binding.myFamilyList
        if (familyList.isNotEmpty()) {
            familyList.forEach {
                val familyItem = View.inflate(
                    activity,
                    R.layout.family_item,
                    null
                )
                familyItem.findViewById<TextView>(R.id.family_name).text =
                    it
                familyItem.setPadding(0, 30, 0, 30)
                myFamilyList.addView(familyItem)

                if (sessionManager.fetchFamilyName() == null) {
                    sessionManager.saveFamilyName(familyList[0])
                    sessionManager.saveFamilyId(response[0].id)
                    familyItem.findViewById<ImageView>(R.id.now_family).isVisible =
                        true
                    val memberList =
                        response.find {num->num.home_name == familyList[0] }!!.family_admin
                    sessionManager.storeFamilyMembers(memberList)
                } else {
                    if (sessionManager.fetchFamilyName() == it) {
                        familyItem.findViewById<ImageView>(R.id.now_family).isVisible =
                            true
                        val memberList =
                            response.find { num->num.home_name == sessionManager.fetchFamilyName() }!!.family_admin
                        sessionManager.storeFamilyMembers(memberList)
                    }
                }
                familyItem.setOnClickListener {view->
                    if (sessionManager.fetchFamilyName() != view.findViewById<TextView>(
                            R.id.family_name
                        ).text.toString()
                    ) {
                        sessionManager.saveFamilyName(view.findViewById<TextView>(R.id.family_name).text.toString())
                        Log.d(
                            "IotApi",
                            "getFamily: ${sessionManager.fetchFamilyName()}"
                        )
                        activity.finish()
                        activity.startActivity(
                            Intent(
                                activity,
                                MainActivity::class.java
                            )
                        )
                    } else {
                        Log.d("IotApi", "getFamily: 已選擇此家庭")
                        Toast.makeText(activity, "已選擇此家庭", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        } else {
            Log.d("IotApi", "getFamily: 沒有家庭")
            sessionManager.storeFamilyMembers(arrayListOf())
        }
        binding.loading.isVisible = false
    }
}