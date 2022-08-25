package com.iotApp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.iotApp.api.Constants
import com.iotApp.api.WsListener
import com.iotApp.databinding.FragmentMainHomeBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import java.util.concurrent.TimeUnit


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentMainHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val host: String = "192.168.0.10"

    //    private val host: String = "192.168.0.15"
    private val mWbSocketUrl = "ws://192.168.0.10:8000" + Constants.Power_Strip_URL
    private lateinit var mClient: OkHttpClient
    private lateinit var request: Request
    private lateinit var mWebSocket: WebSocket

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentMainHomeBinding.inflate(inflater, container, false)



        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //設定全部開關文字
        binding.tplinkSwitch1.text = "開關1 開"
        binding.tplinkSwitch2.text = "開關2 開"
        binding.tplinkSwitch3.text = "開關3 開"
        binding.tplinkSwitch4.text = "開關4 開"
        binding.tplinkSwitch5.text = "開關5 開"
        binding.tplinkSwitch6.text = "開關6 開"

        mClient = OkHttpClient.Builder()
            .pingInterval(10, TimeUnit.SECONDS)
            .build()
        request = Request.Builder()
            .url(mWbSocketUrl)
            .build()
        mWebSocket = mClient.newWebSocket(request, WsListener())

        binding.tplinkSwitch1.setOnClickListener {
            if (binding.tplinkSwitch1.text == "開關1 開") {
                binding.tplinkSwitch1.text = "開關1 關"
                mWebSocket.send("{\"message\":\"off:1\"}")
            } else {
                binding.tplinkSwitch1.text = "開關1 開"
                mWebSocket.send("{\"message\":\"on:1\"}")
            }
        }
        binding.tplinkSwitch2.setOnClickListener {
            if (binding.tplinkSwitch2.text == "開關2 開") {
                binding.tplinkSwitch2.text = "開關2 關"
                mWebSocket.send("{\"message\":\"off:2\"}")
            } else {
                binding.tplinkSwitch2.text = "開關2 開"
                mWebSocket.send("{\"message\":\"on:2\"}")
            }
        }
        binding.tplinkSwitch3.setOnClickListener {
            if (binding.tplinkSwitch3.text == "開關3 開") {
                binding.tplinkSwitch3.text = "開關3 關"
                mWebSocket.send("{\"message\":\"off:3\"}")
            } else {
                binding.tplinkSwitch3.text = "開關3 開"
                mWebSocket.send("{\"message\":\"on:3\"}")
            }
        }
        binding.tplinkSwitch4.setOnClickListener {
            if (binding.tplinkSwitch4.text == "開關4 開") {
                binding.tplinkSwitch4.text = "開關4 關"
                mWebSocket.send("{\"message\":\"off:4\"}")
            } else {
                binding.tplinkSwitch4.text = "開關4 開"
                mWebSocket.send("{\"message\":\"on:4\"}")
            }
        }
        binding.tplinkSwitch5.setOnClickListener {
            if (binding.tplinkSwitch5.text == "開關5 開") {
                binding.tplinkSwitch5.text = "開關5 關"
                mWebSocket.send("{\"message\":\"off:5\"}")
            } else {
                binding.tplinkSwitch5.text = "開關5 開"
                mWebSocket.send("{\"message\":\"on:5\"}")
            }
        }
        binding.tplinkSwitch6.setOnClickListener {
            if (binding.tplinkSwitch6.text == "開關6 開") {
                binding.tplinkSwitch6.text = "開關6 關"
                mWebSocket.send("{\"message\":\"off:6\"}")
            } else {
                binding.tplinkSwitch6.text = "開關6 開"
                mWebSocket.send("{\"message\":\"on:6\"}")
            }
        }

    }

    private fun send(msg: String) {
        mWebSocket.send(msg)
    }

    private fun disconnect(code: Int, reason: String) {
        mWebSocket.close(code, reason)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}