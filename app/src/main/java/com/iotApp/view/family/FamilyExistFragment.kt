package com.iotApp.view.family

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iotApp.R
import com.iotApp.databinding.FragmentFamilyExistBinding
import com.iotApp.repository.SessionManager
import com.iotApp.view.MainActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocketListener
import org.json.JSONObject
import java.util.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FamilyExistFragment : Fragment() {

    private var _binding: FragmentFamilyExistBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFamilyExistBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.btnBack.setOnClickListener {
            activity?.finish()
        }
        binding.btnSendFamilyName.setOnClickListener {

            val client = OkHttpClient()
            val request: Request =
                Request.Builder()
                    .url("wss://api.bap5.cc/ws/chat/${binding.tilFamilyName.editText?.text.toString()}/")
                    .build()
            val webSocket = client.newWebSocket(request, object : WebSocketListener() {})

            fun msgFactory(msg: String): JSONObject {
                val jsonObjects = JSONObject()
                jsonObjects.put("type", "message")
                jsonObjects.put(
                    "message",
                    msg + "%@%" + Calendar.getInstance().time + "%@%" + SessionManager(
                        requireActivity()
                    ).fetchUserInfo()?.username
                )
                return jsonObjects
            }

            fun sendRequest(requestUserName: String) {
                val jsonObject = msgFactory("#request#|$requestUserName")
                webSocket.send(jsonObject.toString())
                Log.d("sendRequest", jsonObject.toString())
            }

            if (binding.tilFamilyName.editText?.text!!.isNotEmpty()) {
                sendRequest(SessionManager(requireActivity()).fetchUserInfo()!!.username)
            } else {
                binding.tilFamilyName.error = "家庭名稱不可為空"
            }
            activity?.finish()
            activity?.startActivity(Intent(activity, MainActivity::class.java))
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_exist_family_to_navigation_family_add)
        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        findNavController().navigate(R.id.action_navigation_exist_family_to_navigation_family_add)
                    }
                }
            )

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

