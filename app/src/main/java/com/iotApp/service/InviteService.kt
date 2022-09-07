package com.iotApp.service

import android.app.Service
import android.os.Binder
import android.util.Log
import android.widget.Toast
import com.iotApp.FamilyActivity
import com.iotApp.repository.SessionManager
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocketListener
import kotlin.concurrent.thread

class InviteService : Service() {
    class InviteServiceBinder : Binder() {
        fun getService(): InviteService {
            return InviteService()
        }
    }

    override fun onBind(intent: android.content.Intent): android.os.IBinder? {
        return null
    }

    override fun onStartCommand(intent: android.content.Intent, flags: Int, startId: Int): Int {
        val sessionManager = SessionManager(this)
        Log.d("InviteService", "onStartCommand")
        Toast.makeText(this, "InviteService started", Toast.LENGTH_SHORT).show()
        val webSocketListener = object : WebSocketListener() {
            override fun onOpen(webSocket: okhttp3.WebSocket, response: okhttp3.Response) {
                super.onOpen(webSocket, response)
                Log.d("InviteService", "onOpen")

            }

            override fun onMessage(webSocket: okhttp3.WebSocket, text: String) {
                super.onMessage(webSocket, text)
                thread {
                    Log.d("InviteService", "got invite")
                    val message = text.split("%@%")
                    if (message[0].contains("#request#|")) {
                        val requestUserName = message[0].split("#|")[1]
                        if (sessionManager.fetchMyOwnFamily()!!
                                .contains(sessionManager.fetchFamilyId())
                        ) {
                            val intent =
                                android.content.Intent(
                                    this@InviteService,
                                    FamilyActivity::class.java
                                )
                            SessionManager(this@InviteService).storeRequestUserName(requestUserName)
                            intent.putExtra("FamilyMemberActivity", "request")
                            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }
                }
            }

            override fun onClosed(webSocket: okhttp3.WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Log.d("InviteService", "onClosed")
            }

            override fun onFailure(
                webSocket: okhttp3.WebSocket,
                t: Throwable,
                response: okhttp3.Response?
            ) {
                super.onFailure(webSocket, t, response)
                Log.d("InviteService", "onFailure")
            }
        }
        val client = OkHttpClient()
        val request: Request =
            Request.Builder()
                .url("wss://api.bap5.cc/ws/chat/${SessionManager(this).fetchFamilyId()}/").build()
        val webSocket = client.newWebSocket(request, webSocketListener)
        return super.onStartCommand(intent, flags, startId)
    }

}