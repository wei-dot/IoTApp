package com.iotApp.controller

import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.TextView
import com.iotApp.R
import com.iotApp.databinding.FragmentMainFamilyBinding
import com.iotApp.repository.SessionManager

class ChatRoomHistoryController {
    fun chatRoomHistoryBuilder(
        binding: FragmentMainFamilyBinding,
        messageList: ArrayList<String>,
        activity: Activity
    ) {
        messageList.forEach {
            val messageArrayList = it.split("%@%")
            val messageListView = binding.chatRoom.messageList
            if (!messageArrayList[0].contains("#request#")){
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
                messageListView.addView(msg)
                binding.chatRoom.messageScrollView.post { binding.chatRoom.messageScrollView.fullScroll(View.FOCUS_DOWN) }
            }
        }
        Log.d("ChatRoomHistoryController", "添加成功")
    }
}