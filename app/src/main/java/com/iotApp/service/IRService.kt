package com.iotApp.service

import android.app.Service
import android.os.Binder
import android.util.Log
import android.widget.Toast
import com.iotApp.R
import com.iotApp.repository.SessionManager
import com.iotApp.view.FamilyActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import kotlin.concurrent.thread

class IRService : Service() {
    inner class IRBinder : Binder() {
        val service: IRService
            get() = this@IRService
    }
    private val webSocketListener = object : WebSocketListener() {
        override fun onOpen(webSocket: okhttp3.WebSocket, response: okhttp3.Response) {
            super.onOpen(webSocket, response)
            Log.d("IRService", "onOpen")
        }

        override fun onClosed(webSocket: okhttp3.WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            Log.d("IRService", "onClosed")
        }

        override fun onFailure(
            webSocket: okhttp3.WebSocket,
            t: Throwable,
            response: okhttp3.Response?
        ) {
            super.onFailure(webSocket, t, response)
            Log.d("IRService", "onFailure")
        }
    }
    private val client = OkHttpClient()
    val request: Request =
        Request.Builder()
            .url("wss://api.bap5.cc/ws/chat/ir/").build()
    val webSocket = client.newWebSocket(request, webSocketListener)

    override fun onBind(intent: android.content.Intent): android.os.IBinder? {
        return null
    }

    override fun onStartCommand(intent: android.content.Intent, flags: Int, startId: Int): Int {
        Log.d("IRService", "onStartCommand")
        Toast.makeText(this, "InviteService started", Toast.LENGTH_SHORT).show()
        return super.onStartCommand(intent, flags, startId)
    }


}