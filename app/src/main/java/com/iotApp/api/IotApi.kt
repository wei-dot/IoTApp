package com.iotApp.api

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.iotApp.MainActivity
import com.iotApp.databinding.*
import com.iotApp.databinding.*
import com.iotApp.controller.SideBarController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body


class IotApi {


    companion object {
        private val apiClient: ApiService = ApiClient.getApiService()
        var handler: Handler = Handler(Looper.getMainLooper())


        fun createHome(
            @Body info: CreateHome,
            activity: FragmentActivity?,
            binding: FragmentFamilyCreateBinding,
            sessionManager: SessionManager
        ) {
            apiClient.createFamily(token = "Token ${sessionManager.fetchAuthToken()}", info)
                .enqueue {
                    onResponse = {
                        if (it.isSuccessful) {
                            Log.d("IotApi", "createHome: 建立家庭成功")
                            Toast.makeText(activity, "建立家庭成功", Toast.LENGTH_SHORT).show()
                            sessionManager.saveFamilyName(info.home_name)

//                            val adminInfo = sessionManager.fetchUserInfo()?.username.toString()
//                                .let { it1 -> Admin(info.home_name, it1) }
//                            setAdmin(adminInfo, activity, binding, sessionManager)
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
            sessionManager: SessionManager
        ) {
            apiClient.setAdmin(token = "Token ${sessionManager.fetchAuthToken()}", admin)
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
            sessionManager: SessionManager
        ) {
            binding.loading.isVisible = true
            apiClient.getFamily(token = "Token ${sessionManager.fetchAuthToken()}").enqueue {
                onResponse = { it ->
                    if (it.isSuccessful) {
                        val response = it.body()!!
                        val familyList: List<String> = response.map { num -> num.home_name }
                        SideBarController().sideBar(
                            activity!!,
                            binding,
                            sessionManager,
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
            sessionManager: SessionManager
        ) {
            apiClient.getFamilyAdmin(token = "Token ${sessionManager.fetchAuthToken()}").enqueue() {
                onResponse = {
                    if (it.isSuccessful) {
                        val response = it.body()!!
                        val myOwnFamilyList: List<String> =
                            response.map { num -> num.home_id }.toList()
                        sessionManager.saveMyOwnFamily(myOwnFamilyList)
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
            sessionManager: SessionManager,
            @Body info: AlterHome
        ) {
            apiClient.alterFamily(
                sessionManager.fetchFamilyId().toString(),
                token = "Token ${sessionManager.fetchAuthToken()}",
                info
            ).enqueue {
                onResponse = {
                    if (it.isSuccessful) {
                        Log.d("IotApi", "delFamilyMember: 刪除成功")
                        Toast.makeText(activity, "刪除成功", Toast.LENGTH_SHORT).show()
                        binding.loading.isVisible = false
                        sessionManager.storeFamilyMembers(info.user)
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

        fun updateFamilyMemberByFamilyID(sessionManager: SessionManager) {
            val nowFamilyID = sessionManager.fetchFamilyId()
            apiClient.getFamilyMember(
                id = nowFamilyID.toString(),
                token = "Token ${sessionManager.fetchAuthToken()}"
            ).enqueue {
                onResponse = {
                    if (it.isSuccessful) {
                        val response = it.body()!!
                        sessionManager.storeFamilyMembers(response.family_member)
                        Log.d(
                            "IotApi",
                            "getFamilyMemberByFamilyID: 更新成功\t ${sessionManager.fetchFamilyMembers()}"
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
            sessionManager: SessionManager,
            @Body info: AlterHome
        ) {
            apiClient.alterFamily(
                id = sessionManager.fetchFamilyId().toString(),
                token = "Token ${sessionManager.fetchAuthToken()}",
                info
            ).enqueue {
                onResponse = {
                    if (it.isSuccessful) {
                        Log.d("IotApi", "exitFamily: 退出家庭成功")
                        Toast.makeText(activity, "退出家庭成功", Toast.LENGTH_SHORT).show()
                        sessionManager.clearFamilyId()
                        sessionManager.clearFamilyName()
                        sessionManager.clearFamilyMembers()
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
            sessionManager: SessionManager
        ) {
            apiClient.deleteFamily(
                id = sessionManager.fetchFamilyId().toString(),
                token = "Token ${sessionManager.fetchAuthToken()}"
            ).enqueue {
                onResponse = {
                    if (it.isSuccessful) {
                        Log.d("IotApi", "deleteFamily: 刪除家庭成功")
                        Toast.makeText(activity, "刪除家庭成功", Toast.LENGTH_SHORT).show()
                        sessionManager.clearFamilyId()
                        sessionManager.clearFamilyName()
                        sessionManager.clearFamilyMembers()
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
        fun getModeKeyInfo(activity: FragmentActivity?, sessionManager: SessionManager) {
            Log.d("IotApi", "getModeKeyInfo: Token ${sessionManager.fetchAuthToken()}")
            apiClient.getModeKeyDataInfo(token = "Token ${sessionManager.fetchAuthToken()}")
                .enqueue {
                    onResponse = {
                        if (it.isSuccessful) {
                            Log.d("IotApi", "getModeKeyInfo: 取得組合鍵金鑰成功")
                            val response = it.body()!!
                            var modeKeyList = response
                            modeKeyList = removeModeKey(modeKeyList, sessionManager)
                            sessionManager.saveModeKeyData(modeKeyList)
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

        fun deleteModeKey(activity: FragmentActivity?, sessionManager: SessionManager, keyId: Int) {
            apiClient.deleteModeKey(id = keyId, token = "Token ${sessionManager.fetchAuthToken()}")
                .enqueue {
                    onResponse = {
                        if (it.isSuccessful) {
                            Log.d("IotApi", "deleteMode: 刪除組合鍵成功")
                            Toast.makeText(activity, " 刪除組合鍵成功", Toast.LENGTH_SHORT).show()
                            activity?.let { it1 -> SessionManager(it1) }?.let { it2 ->
                                IotApi.getModeKeyInfo(
                                    activity,
                                    it2
                                )
                            }
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
            sessionManager: SessionManager
        ): ArrayList<GetModeKeyDataInfo> {

            for (i in 0 until modeKeyList.size) {
                if (modeKeyList[i].home_id.toString() != sessionManager.fetchFamilyId()) {
                    modeKeyList.remove(modeKeyList[i])
                    removeModeKey(modeKeyList, sessionManager)
                    break
                }
            }
            return modeKeyList
        }

        fun postModeKeyInfo(
            activity: FragmentActivity?,
            sessionManager: SessionManager,
            @Body info: PostModeKeyDataInfo
        ) {
            apiClient.postModeKeyDataInfo(token = "Token ${sessionManager.fetchAuthToken()}", info)
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