package com.iotApp.view.main.family

import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.ThreadUtils.runOnUiThread
import com.iotApp.R
import com.iotApp.api.IotApi
import com.iotApp.databinding.FragmentMainFamilyBinding
import com.iotApp.repository.SessionManager
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class ChatRoomEmbed {
    fun chatRoomEmbed(activity: FragmentActivity, binding: FragmentMainFamilyBinding) {
        binding.chatRoom.messageEdit.isClickable = false
        binding.chatRoom.sendBtn.isClickable = false
        binding.chatRoom.loading.isVisible = true
        IotApi.getChatRoomHistory(
            activity, binding,
            SessionManager(activity), SessionManager(activity).fetchFamilyId().toString()
        )

        val webSocketListener = object : WebSocketListener() {
            override fun onOpen(
                webSocket: WebSocket, response: Response
            ) {
                super.onOpen(webSocket, response)
                // Do something when the connection is open
                Log.d("WebSocket", "Connection Opened")
                runOnUiThread {
                    binding.chatRoom.messageEdit.isClickable = true
                    binding.chatRoom.sendBtn.isClickable = true
                    binding.chatRoom.loading.isVisible = false
                    Toast.makeText(activity, "現在可以傳遞訊息了", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onMessage(
                webSocket: WebSocket, text: String
            ) {
                super.onMessage(webSocket, text)
                // Do something when you get the server message
                runOnUiThread {
                    try {
                        val jsonObject = JSONObject(text)
                        val message = jsonObject.getString("message")
                        val messageArrayList = message.split("%@%")
                        val messageList = binding.chatRoom.messageList
                        if (!messageArrayList[0].contains("#request#")) {
                            val msg = View.inflate(
                                activity,
                                if (messageArrayList[2] == SessionManager(activity).fetchUserInfo()?.username) R.layout.item_msg else R.layout.item_msg_rev,
                                null
                            )
                            val msgContent = msg.findViewById<TextView>(R.id.ThisIsMsg)
                            val msgTime = msg.findViewById<TextView>(R.id.ThisIsMsgTime)
                            val msgAuthor = msg.findViewById<TextView>(R.id.ThisIsMsgAuthor)
                            msgContent.text = messageArrayList[0]
                            msgTime.text = messageArrayList[1]
                            msgAuthor.text = messageArrayList[2]
                            msg.setPadding(0, 0, 0, 20)
                            messageList.addView(msg)
                            binding.chatRoom.messageScrollView.post {
                                binding.chatRoom.messageScrollView.fullScroll(
                                    View.FOCUS_DOWN
                                )
                            }
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onClosing(
                webSocket: WebSocket, code: Int, reason: String
            ) {
                super.onClosed(webSocket, code, reason)
                // Do something when the connection is closing
                Log.d("WebSocket", "Connection Closed")
                activity.finish()
            }

        }
        val client = OkHttpClient()
        val request: Request =
            Request.Builder()
                .url("wss://api.bap5.cc/ws/chat/${SessionManager(activity).fetchFamilyId()}/")
                .build()
        val webSocket = client.newWebSocket(request, webSocketListener)
        binding.chatRoom.sendBtn.setOnClickListener {
            if (binding.chatRoom.messageEdit.text.toString().isNotEmpty()) {
                val jsonObjects = msgFactory(binding.chatRoom.messageEdit.text.toString(), activity)
                webSocket.send(jsonObjects.toString())
                binding.chatRoom.messageEdit.text?.clear()
            }
        }
    }

    private fun msgFactory(msg: String, activity: FragmentActivity): JSONObject {
        val jsonObjects = JSONObject()
        jsonObjects.put("type", "message")
        jsonObjects.put(
            "message",
            msg + "%@%" + Calendar.getInstance().time + "%@%" + SessionManager(activity).fetchUserInfo()?.username
        )
        return jsonObjects
    }
}