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
import com.iotApp.adapter.ChatRoomHistoryController
import com.iotApp.adapter.SideBarController
import com.iotApp.databinding.DrawerUserProfileBinding
import com.iotApp.databinding.FragmentFamilyCreateBinding
import com.iotApp.databinding.FragmentFamilyRequestBinding
import com.iotApp.databinding.FragmentMainFamilyBinding
import com.iotApp.model.AlterHome
import com.iotApp.model.CreateHome
import com.iotApp.model.GetModeKeyDataInfo
import com.iotApp.model.PostModeKeyDataInfo
import com.iotApp.repository.SessionManager
import com.iotApp.view.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body


class IotApi {


    companion object {
        private val apiClient: ApiService = ApiClient.client?.create(ApiService::class.java)!!
        var handler: Handler = Handler(Looper.getMainLooper())


        /**
         * Function to sign up user
         */
//

        /**
         * Function to get UserInfo
         */
        fun getInfo(activity: Activity?, sessionManager: SessionManager) {
            apiClient.getInfo(token = "Token ${sessionManager.fetchAuthToken()}")
                .enqueue {
                    onResponse = {
                        val msg = Message()
                        if (it.isSuccessful) {
                            val response = it.body()!!
                            Log.d("IotApi getInfo", "Success: $response")
                            Toast.makeText(
                                activity,
                                "歡迎 ${response.username} 回來",
                                Toast.LENGTH_SHORT
                            ).show()
                            msg.obj = response
                            handler.sendMessage(msg)
                        } else {
                            val response = it.errorBody()?.string()
                            Log.d("IotApi getInfo", "$response")
                            Toast.makeText(
                                activity,
                                "取得失敗: $response",
                                Toast.LENGTH_SHORT
                            ).show()
                            msg.obj = null
                            handler.sendMessage(msg)
                        }
                    }
                    onFailure = {
                        Log.d("IotApi", "getInfo: ${it?.message}")
                        Toast.makeText(activity, "取得錯誤", Toast.LENGTH_SHORT).show()
                    }
                }
        }

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
                            sessionManager.clearFamilyId()
                            binding.loading.isVisible = false
                            activity?.finish()
                            activity?.startActivity(Intent(activity, MainActivity::class.java))
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
            sessionManager: SessionManager
        ) {
            apiClient.getFamilyAdmin(token = "Token ${sessionManager.fetchAuthToken()}").enqueue {
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

        fun addFamilyMember(
            activity: FragmentActivity?,
            binding: FragmentFamilyRequestBinding,
            sessionManager: SessionManager,
            @Body info: AlterHome
        ) {
            binding.loading.isVisible = true
            apiClient.alterFamily(
                sessionManager.fetchFamilyId().toString(),
                token = "Token ${sessionManager.fetchAuthToken()}",
                info
            ).enqueue {
                onResponse = {
                    if (it.isSuccessful) {
                        Log.d("IotApi", "addFamilyMember: 新增成功")
                        Toast.makeText(activity, "新增成功", Toast.LENGTH_SHORT).show()
                        binding.loading.isVisible = false
                        sessionManager.storeFamilyMembers(info.user)
                        activity?.finish()
                        activity?.startActivity(Intent(activity, MainActivity::class.java))
                    } else {
                        Log.d("IotApi", "addFamilyMember: 新增失敗")
                        Toast.makeText(binding.root.context, "新增失敗", Toast.LENGTH_SHORT).show()
                        binding.loading.isVisible = false
                        activity?.finish()
                    }
                }
                onFailure = {
                    Log.d("IotApi", "addFamilyMember: ${it?.message}")
                    Toast.makeText(binding.root.context, "新增失敗", Toast.LENGTH_SHORT).show()
                    binding.loading.isVisible = false
                    activity?.finish()
                }
            }
        }

        fun exitFamily(
            activity: FragmentActivity?,
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

        fun deleteFamily(activity: FragmentActivity?, sessionManager: SessionManager) {
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
                if (modeKeyList[i].home_id != sessionManager.fetchFamilyId()) {
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

        fun getChatRoomHistory(
            activity: Activity?,
            binding: FragmentMainFamilyBinding,
            sessionManager: SessionManager,
            chatRoomId: String
        ) {
            apiClient.getChatRoomHistory(
                token = "Token ${sessionManager.fetchAuthToken()}",
                room_name = chatRoomId
            )
                .enqueue {
                    onResponse = {
                        if (it.isSuccessful) {
                            Log.d("IotApi", "getChatRoomHistory: 取得聊天室歷史訊息成功")
                            val response = it.body()!!
                            Log.d("IotApi", response.toString())
                            val messageIdList = response.message
                            getMessageContent(activity, binding, sessionManager, messageIdList)


                        } else {
                            Log.d("IotApi", "getChatRoomHistory: 取得聊天室歷史訊息失敗")
                            Toast.makeText(
                                activity,
                                "取得聊天室歷史訊息失敗: ${it.errorBody()?.string()} ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    onFailure = {
                        Log.d("IotApi", "getChatRoomHistory: ${it?.message}")
                        Toast.makeText(activity, "取得聊天室歷史訊息失敗", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        private fun getMessageContent(
            activity: Activity?,
            binding: FragmentMainFamilyBinding,
            sessionManager: SessionManager,
            messageIdList: ArrayList<String>
        ) {
            val messageList: ArrayList<String> = ArrayList()
            messageIdList.forEach {
                apiClient.getMessageContent(
                    token = "Token ${sessionManager.fetchAuthToken()}",
                    id = it
                )
                    .enqueue {
                        onResponse = {
                            if (it.isSuccessful) {
                                Log.d("IotApi", "getMessageContent: 取得訊息內容成功")
                                val response = it.body()!!
                                Log.d("IotApi", response.toString())
                                messageList.add(response.message)

                            } else {
                                Log.d("IotApi", "getMessageContent: 取得訊息內容失敗")
                                Toast.makeText(
                                    activity,
                                    "取得訊息內容失敗: ${it.errorBody()?.string()} ",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                        onFailure = { it ->
                            Log.d("IotApi", "getMessageContent: ${it?.message}")
                            Toast.makeText(activity, "取得訊息內容失敗", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            Handler(Looper.getMainLooper()).postDelayed({
                ChatRoomHistoryController().chatRoomHistoryBuilder(binding, messageList, activity!!)
            }, 5000)

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


