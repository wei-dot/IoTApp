package com.iotApp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.iotApp.R.id.*
import com.iotApp.api.IotApi

import com.iotApp.databinding.ActivityChatRoomDemoBinding
import com.iotApp.repository.SessionManager
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class ChatRoomDemo : AppCompatActivity() {
    private lateinit var binding: ActivityChatRoomDemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.messageEdit.isClickable = false
        binding.sendBtn.isClickable = false
        binding.loading.isVisible = true
        IotApi.getChatRoomHistory(
            this, binding,
            SessionManager(this), SessionManager(this).fetchFamilyId().toString()
        )

        val webSocketListener = object : WebSocketListener() {
            override fun onOpen(
                webSocket: WebSocket, response: Response
            ) {
                super.onOpen(webSocket, response)
                // Do something when the connection is open
                Log.d("WebSocket", "Connection Opened")
                runOnUiThread {
                    binding.messageEdit.isClickable = true
                    binding.sendBtn.isClickable = true
                    binding.loading.isVisible = false
                    Toast.makeText(this@ChatRoomDemo, "現在可以傳遞訊息了", Toast.LENGTH_SHORT).show()
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
                        var message = jsonObject.getString("message")
                        val messageArrayList = message.split("%@%")
                        val messageList = binding.messageList
                        val msg = View.inflate(
                            this@ChatRoomDemo,
                            if (messageArrayList[2] == SessionManager(this@ChatRoomDemo).fetchUserInfo()?.username) R.layout.item_msg else R.layout.item_msg_rev,
                            null
                        )
                        val msgContent = msg.findViewById<TextView>(ThisIsMsg)
                        val msgTime = msg.findViewById<TextView>(ThisIsMsgTime)
                        val msgAuthor = msg.findViewById<TextView>(ThisIsMsgAuthor)
                        msgContent.text = messageArrayList[0]
                        msgTime.text = messageArrayList[1]
                        msgAuthor.text = messageArrayList[2]
                        msg.setPadding(0, 0, 0, 20)
                        messageList.addView(msg)
                        binding.messageScrollView.post { binding.messageScrollView.fullScroll(View.FOCUS_DOWN) }
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
                finish()
            }

        }
        val client = OkHttpClient()
        val request: Request =
            Request.Builder()
                .url("wss://api.bap5.cc/ws/chat/${SessionManager(this).fetchFamilyId()}/").build()
        val webSocket = client.newWebSocket(request, webSocketListener)
        binding.sendBtn.setOnClickListener {
            if (binding.messageEdit.text.toString().isNotEmpty()) {
                val jsonObjects = msgFactory(binding.messageEdit.text.toString())
                webSocket.send(jsonObjects.toString())
                binding.messageEdit.text.clear()
            }
        }
    }

    fun msgFactory(msg: String): JSONObject {
        val jsonObjects = JSONObject()
        jsonObjects.put("type", "message")
        jsonObjects.put(
            "message",
            msg + "%@%" + Calendar.getInstance().time + "%@%" + SessionManager(this).fetchUserInfo()?.username
        )
        return jsonObjects
    }

}