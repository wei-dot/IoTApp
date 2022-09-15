package com.iotApp.api

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.json.JSONObject

class WsListener(val context: Context) : WebSocketListener() {
    companion object {
        var handler: Handler = Handler(Looper.getMainLooper())
    }
    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        Log.d("HomeFragment", "onOpen")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        val json = JSONObject(text)
        val message = JSONObject(json.getString("message"))
        val msg = Message()
        msg.obj=message
        handler.sendMessage(msg)



//        val type = json.getJSONObject("device_type")


    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        Toast.makeText(context, bytes.toString(), Toast.LENGTH_SHORT).show()
        Log.d("HomeFragment", "onMessage")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        Log.d("HomeFragment", "onClosing")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        Log.d("HomeFragment", "onClosed")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        Log.d("HomeFragment", "onFailure")
    }


}