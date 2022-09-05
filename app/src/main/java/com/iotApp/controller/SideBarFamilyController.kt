package com.iotApp.controller
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.iotApp.R
import com.iotApp.api.Home
import com.iotApp.databinding.DrawerUserProfileBinding
import com.iotApp.repository.SessionManager
import com.iotApp.view.MainActivity

class SideBarController {
    fun sideBar(activity : Activity , binding : DrawerUserProfileBinding,context: Context , familyList : List<String>, response : ArrayList<Home>) {
        val myFamilyList: LinearLayout = binding.myFamilyList
        if (familyList.isNotEmpty()) {
            var n = 0
            familyList.forEach {
                val familyItem = View.inflate(
                    activity,
                    R.layout.item_family,
                    null
                )
                familyItem.findViewById<TextView>(R.id.family_name).text =
                    it
                val nowID : String = response[n].id
                familyItem.setTag(R.id.family_name, nowID)
                familyItem.setPadding(0, 30, 0, 30)
                myFamilyList.addView(familyItem)
                if (SessionManager.getFamilyName(context) == null) {
                    SessionManager.saveFamilyName(context,familyList[0])
                    SessionManager.saveFamilyId(context,response[0].id)
                    familyItem.findViewById<ImageView>(R.id.now_family).isVisible =
                        true
                    val memberList =
                        response.find {num->num.home_name == familyList[0] }!!.family_member
                    SessionManager.saveFamilyMembers(context,memberList)
                } else {
                if (SessionManager.getFamilyId(context) == familyItem.getTag(R.id.family_name)) {

                        familyItem.findViewById<ImageView>(R.id.now_family).isVisible =
                            true
                        val memberList =
                            response.find { num->num.home_name == SessionManager.getFamilyName(context) }!!.family_member
                        SessionManager.saveFamilyMembers(context,memberList)
                    }
                }
                familyItem.setOnClickListener {view->
                    if (SessionManager.getFamilyId(context) != familyItem.getTag(R.id.family_name)
                    ) {
                        SessionManager.saveFamilyName(context,response.find { home: Home -> home.id == familyItem.getTag(R.id.family_name) }!!.home_name)
                        SessionManager.saveFamilyId(context,familyItem.getTag(R.id.family_name) as String)
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
                n++
            }
        } else {
            Log.d("IotApi", "getFamily: 沒有家庭")
            SessionManager.saveFamilyMembers(context,arrayListOf())
        }
        binding.loading.isVisible = false
    }
}