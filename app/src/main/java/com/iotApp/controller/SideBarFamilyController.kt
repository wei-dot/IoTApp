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
import com.iotApp.databinding.DrawerUserProfileBinding

<<<<<<< HEAD:app/src/main/java/com/iotApp/controller/SideBarController.kt
class SideBarController {
    fun sideBar(activity : Activity , binding : DrawerUserProfileBinding,sessionManager: SessionManager , familyList : List<String>, response : ArrayList<Home>) {
=======
class SideBarFamilyController {
    fun sideBar(activity : Activity , binding : com.iotApp.databinding.UserProfileBinding,sessionManager: SessionManager , familyList : List<String>, response : ArrayList<Home>) {
>>>>>>> a0dcdff7b7495eecd9f8f63fbe088173e5a12906:app/src/main/java/com/iotApp/controller/SideBarFamilyController.kt
        val myFamilyList: LinearLayout = binding.myFamilyList
        if (familyList.isNotEmpty()) {
            familyList.forEach {
                val familyItem = View.inflate(
                    activity,
                    R.layout.item_family,
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
                        response.find {num->num.home_name == familyList[0] }!!.family_member
                    sessionManager.storeFamilyMembers(memberList)
                } else {
                    sessionManager.saveFamilyId(response.find { home: Home -> home.home_name == sessionManager.fetchFamilyName() }!!.id)
                    sessionManager.saveFamilyName(response.find { home: Home -> home.home_name == sessionManager.fetchFamilyName() }!!.home_name)
                    if (sessionManager.fetchFamilyName() == it) {
                        familyItem.findViewById<ImageView>(R.id.now_family).isVisible =
                            true
                        val memberList =
                            response.find { num->num.home_name == sessionManager.fetchFamilyName() }!!.family_member
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