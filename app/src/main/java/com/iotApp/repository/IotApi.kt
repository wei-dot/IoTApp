package com.iotApp.repository

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.iotApp.api.*
import com.iotApp.controller.SideBarController
import com.iotApp.databinding.DrawerUserProfileBinding
import com.iotApp.databinding.FragmentFamilyCreateBinding
import com.iotApp.databinding.FragmentFamilyEditBinding
import com.iotApp.databinding.FragmentMainFamilyBinding
import com.iotApp.view.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body

class IotApi {


    companion object {
        private val apiClient = ApiClient.client!!.create(ApiInterface::class.java)
        var handler: Handler = Handler(Looper.getMainLooper())


        fun createHome(
            @Body info: CreateHome,
            activity: FragmentActivity?,
            binding: FragmentFamilyCreateBinding,
            context: Context
        ) {
            apiClient.createFamily(token = "Token ${SessionManager.getToken(context)}", info)
                .enqueue {
                    onResponse = {
                        if (it.isSuccessful) {
                            Log.d("IotApi", "createHome: 建立家庭成功")
                            Toast.makeText(activity, "建立家庭成功", Toast.LENGTH_SHORT).show()
                            SessionManager.saveFamilyName(context, info.home_name)

                            val adminInfo = SessionManager.getUsername(context).toString()
                                .let { it1 -> Admin(info.home_name, it1) }
                            setAdmin(adminInfo, activity, binding, context)
                        } else {
                            binding.loading.isVisible = false
                            Log.d("IotApi", "建立家庭失敗: ${it.errorBody()?.string()} ")
                        }
                    }
                    onFailure = {
                        binding.loading.isVisible = false
                        Log.d("IotApi", "createHome: ${it?.message}")
                        Toast.makeText(activity, "建立家庭失敗", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        fun setAdmin(
            @Body admin: Admin,
            activity: FragmentActivity?,
            binding: FragmentFamilyCreateBinding,
            context: Context
        ) {
            apiClient.setAdmin(token = "Token ${SessionManager.getToken(context)}", admin)
                .enqueue {
                    onResponse = {
                        if (it.isSuccessful) {       //這裡目前不清楚為何會明明成功卻掉到"設定家庭成功，但設定管理員失敗"，非延遲問題
                            Log.d("IotApi", "setAdmin: 設定管理員成功")
                            binding.loading.isVisible = false
                            activity?.finish()
                            activity?.finish()
                            activity?.startActivity(Intent(activity, MainActivity::class.java))
                        } else {
                            Log.d("IotApi", "setAdmin: 設定家庭成功，但設定管理員失敗")
                            binding.loading.isVisible = false
                            activity?.finish()
                            activity?.finish()
                            activity?.startActivity(Intent(activity, MainActivity::class.java))
                        }
                    }
                    onFailure = {
                        Log.d("IotApi", "setAdmin: ${it?.message}")
                        binding.loading.isVisible = false
                        activity?.finish()
                        activity?.finish()
                        activity?.startActivity(Intent(activity, MainActivity::class.java))
                    }
                }
        }

        fun getFamily(
            activity: Activity?,
            binding: DrawerUserProfileBinding,
            context: Context
        ) {
            binding.loading.isVisible = true
            apiClient.getFamily(token = "Token ${SessionManager.getToken(context)}").enqueue {
                onResponse = { it ->
                    if (it.isSuccessful) {
                        val response = it.body()!!
                        val familyList: List<String> = response.map { num -> num.home_name }
                        SideBarController().sideBar(
                            activity!!,
                            binding,
                            context,
                            familyList,
                            response
                        )
                    } else {
                        binding.loading.isVisible = false
                        Log.d("IotApi", "getFamily: 取得家庭失敗")
                        Toast.makeText(
                            activity,
                            "取得家庭失敗: ${it.errorBody()?.string()} ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                onFailure = {
                    binding.loading.isVisible = false
                    Log.d("IotApi", "getFamily: ${it?.message}")
                    Toast.makeText(activity, "取得家庭失敗", Toast.LENGTH_SHORT).show()
                }
            }
        }

        fun getMyOwnFamily(
            activity: FragmentActivity?,
            context: Context
        ) {
            apiClient.getFamilyAdmin(token = "Token ${SessionManager.getToken(context)}")
                .enqueue {
                    onResponse = {
                        if (it.isSuccessful) {
                            val response = it.body()!!
                            val myOwnFamilyList: List<String> =
                                response.map { num -> num.home_id }.toList()
                            SessionManager.saveMyOwnFamily(context, myOwnFamilyList)
                        } else {
                            Log.d("IotApi", "getMyOwnFamily: 取得家庭失敗")
                        }
                    }
                    onFailure = {
                        Log.d("IotApi", "getMyOwnFamily: ${it?.message}")
                    }
                }
        }

        fun delFamilyMember(
            activity: FragmentActivity?,
            binding: FragmentMainFamilyBinding,
            context: Context,
            @Body info: AlterHome
        ) {
            apiClient.alterFamily(
                SessionManager.getFamilyId(context).toString(),
                token = "Token ${SessionManager.getToken(context)}",
                info
            ).enqueue {
                onResponse = {
                    if (it.isSuccessful) {
                        Log.d("IotApi", "delFamilyMember: 刪除成功")
                        Toast.makeText(activity, "刪除成功", Toast.LENGTH_SHORT).show()
                        binding.loading.isVisible = false
                        SessionManager.saveFamilyMembers(context, info.user)
                    } else {
                        Log.d("IotApi", "delFamilyMember: 刪除失敗")
                        Toast.makeText(binding.root.context, "刪除失敗", Toast.LENGTH_SHORT).show()
                        binding.loading.isVisible = false
                    }
                }
                onFailure = {
                    Log.d("IotApi", "delFamilyMember: ${it?.message}")
                    Toast.makeText(binding.root.context, "刪除失敗", Toast.LENGTH_SHORT).show()
                    binding.loading.isVisible = false
                }
            }
        }

        fun updateFamilyMemberByFamilyID(context: Context) {
            val nowFamilyID = SessionManager.getFamilyId(context)
            apiClient.getFamilyMember(
                id = nowFamilyID.toString(),
                token = "Token ${SessionManager.getToken(context)}"
            ).enqueue {
                onResponse = {
                    if (it.isSuccessful) {
                        val response = it.body()!!
                        SessionManager.saveFamilyMembers(context, response.family_member)
                        Log.d(
                            "IotApi",
                            "getFamilyMemberByFamilyID: 更新成功\t ${
                                SessionManager.getFamilyMembers(
                                    context
                                )
                            }"
                        )
                    } else {
                        Log.d("IotApi", "getFamilyMemberByFamilyID: 取得家庭成員失敗")
                    }
                }
                onFailure = {
                    Log.d("IotApi", "getFamilyMemberByFamilyID: ${it?.message}")
                }
            }
        }

        fun exitFamily(
            activity: FragmentActivity?,
            binding: FragmentFamilyEditBinding,
            @Body info: AlterHome,
            context: Context
        ) {
            apiClient.alterFamily(
                id = SessionManager.getFamilyId(context).toString(),
                token = "Token ${SessionManager.getToken(context)}",
                info
            ).enqueue {
                onResponse = {
                    if (it.isSuccessful) {
                        Log.d("IotApi", "exitFamily: 退出家庭成功")
                        Toast.makeText(activity, "退出家庭成功", Toast.LENGTH_SHORT).show()
                        SessionManager.clearFamilyId(context)
                        SessionManager.clearFamilyName(context)
                        SessionManager.clearFamilyMembers(context)
                        activity?.finish()
                        activity?.startActivity(Intent(activity, MainActivity::class.java))
                    } else {
                        Log.d("IotApi", "exitFamily: 退出家庭失敗")
                        Toast.makeText(activity, "退出家庭失敗", Toast.LENGTH_SHORT).show()
                    }
                }
                onFailure = {
                    Log.d("IotApi", "exitFamily: ${it?.message}")
                    Toast.makeText(activity, "退出家庭失敗", Toast.LENGTH_SHORT).show()
                }
            }
        }

        fun deleteFamily(
            activity: FragmentActivity?,
            binding: FragmentFamilyEditBinding,
            context: Context
        ) {
            apiClient.deleteFamily(
                id = SessionManager.getFamilyId(context).toString(),
                token = "Token ${SessionManager.getToken(context)}"
            ).enqueue {
                onResponse = {
                    if (it.isSuccessful) {
                        Log.d("IotApi", "deleteFamily: 刪除家庭成功")
                        Toast.makeText(activity, "刪除家庭成功", Toast.LENGTH_SHORT).show()
                        SessionManager.clearFamilyId(context)
                        SessionManager.clearFamilyName(context)
                        SessionManager.clearFamilyMembers(context)
                        activity?.finish()
                        activity?.startActivity(Intent(activity, MainActivity::class.java))
                    } else {
                        Log.d("IotApi", "deleteFamily: 刪除家庭失敗")
                        Toast.makeText(activity, "刪除家庭失敗", Toast.LENGTH_SHORT).show()
                    }
                }
                onFailure = {
                    Log.d("IotApi", "deleteFamily: ${it?.message}")
                    Toast.makeText(activity, "刪除家庭失敗", Toast.LENGTH_SHORT).show()
                }
            }
        }

        /**
         * Function to get Mode Key Info
         */
//        @Body info: GetModeKeyDataInfo,
        fun getModeKeyInfo(activity: FragmentActivity?, context: Context) {
            apiClient.getModeKeyDataInfo(token = "Token ${SessionManager.getToken(context)}")
                .enqueue {
                    onResponse = {
                        if (it.isSuccessful) {
                            Log.d("IotApi", "getModeKeyInfo: 取得組合鍵金鑰成功")
                            val response = it.body()!!
                            var modeKeyList = response
                            modeKeyList = removeModeKey(modeKeyList, context)
                        } else {
                            Log.d("IotApi onResponse ", "getModeKeyInfo: 取得組合鍵金鑰失敗")
                            Toast.makeText(
                                activity,
                                "取得組合鍵金鑰失敗: ${it.errorBody()?.string()} ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    onFailure = {
//                        val users = gson.fromJson<ArrayList<GetModeKeyDataInfo>>(it, type)
//                        Log.d("IotApi onFailure", "getModeKeyInfo data : ${it?.toString()}")
                        Toast.makeText(activity, "取得組合鍵金鑰失敗", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        fun deleteModeKey(activity: FragmentActivity?, context: Context, keyId: Int) {
            apiClient.deleteModeKey(id = keyId, token = "Token ${SessionManager.getToken(context)}")
                .enqueue {
                    onResponse = {
                        if (it.isSuccessful) {
                            Log.d("IotApi", "deleteMode: 刪除組合鍵成功")
                            Toast.makeText(activity, " 刪除組合鍵成功", Toast.LENGTH_SHORT).show()
                            getModeKeyInfo(activity, context)
//                        activity?.finish()
//                        activity?.startActivity(Intent(activity, MainActivity::class.java))
                        } else {
                            Log.d("IotApi", "deleteFamily: 刪除組合鍵失敗")
                            Toast.makeText(activity, "刪除組合鍵失敗", Toast.LENGTH_SHORT).show()
                        }
                    }
                    onFailure = {
                        Log.d("IotApi", "deleteFamily: ${it?.message}")
                        Toast.makeText(activity, "刪除組合鍵失敗", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        private fun removeModeKey(
            modeKeyList: ArrayList<GetModeKeyDataInfo>,
            context: Context
        ): ArrayList<GetModeKeyDataInfo> {

            for (i in 0 until modeKeyList.size) {
                if (modeKeyList[i].home_id.toString() != SessionManager.getFamilyId(context)) {
                    modeKeyList.remove(modeKeyList[i])
                    removeModeKey(modeKeyList, context)
                    break
                }
            }
            return modeKeyList
        }

        fun postModeKeyInfo(
            activity: FragmentActivity?,
            context: Context,
            @Body info: PostModeKeyDataInfo
        ) {
            apiClient.postModeKeyDataInfo(token = "Token ${SessionManager.getToken(context)}", info)
                .enqueue {
                    onResponse = {
                        if (it.isSuccessful) {
                            Log.d("IotApi", "getModeKeyInfo: 取得組合鍵金鑰成功")
                            Toast.makeText(activity, "取得組合鍵金鑰成功", Toast.LENGTH_SHORT).show()
//                            val response = it.body()!!
//                            Log.d("IotApi", response.toString())
                        } else {
                            Log.d("IotApi", "getModeKeyInfo: 取得組合鍵金鑰失敗")
                            Toast.makeText(
                                activity,
                                "取得組合鍵金鑰失敗: ${it.errorBody()?.string()} ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    onFailure = {
                        Log.d("IotApi", "getModeKeyInfo: ${it?.message}")
                        Toast.makeText(activity, "取得組合鍵金鑰失敗", Toast.LENGTH_SHORT).show()
                    }
                }
        }


        private fun <T> Call<T>.enqueue(callback: CallBackKt<T>.() -> Unit) {
            val callBackKt = CallBackKt<T>()
            callback.invoke(callBackKt)
            this.enqueue(callBackKt)
        }

        class CallBackKt<T> : Callback<T> {

            var onResponse: ((Response<T>) -> Unit)? = null
            var onFailure: ((t: Throwable?) -> Unit)? = null

            override fun onFailure(call: Call<T>, t: Throwable) {
                onFailure?.invoke(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                onResponse?.invoke(response)
            }

        }
    }
}