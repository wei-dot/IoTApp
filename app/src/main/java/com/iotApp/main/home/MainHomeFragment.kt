package com.iotApp.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.iotApp.HomeActivity
import com.iotApp.R
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
class MainHomeFragment : Fragment() {

    private var _binding: FragmentMainHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var mHomeFab: ExtendedFloatingActionButton? = null
    private var mAddDeviceFab: FloatingActionButton? = null
    private var mDeviceListFab: FloatingActionButton? = null
    private var mAddDeviceText: TextView? = null
    private var mDeviceListText: TextView? = null
    private var mIsAllFabVisible: Boolean? = null

    //    private val host: String = "192.168.0.15"
    private val host: String = "192.168.0.10"
    private val mWbSocketUrl = "ws://" + host + Constants.Power_Strip_URL
    private lateinit var mClient: OkHttpClient
    private lateinit var request: Request
    private lateinit var mWebSocket: WebSocket

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel =
            ViewModelProvider(this)[MainHomeViewModel::class.java]

        _binding = FragmentMainHomeBinding.inflate(inflater, container, false)
        mHomeFab = binding.homeFab
        mAddDeviceFab = binding.addDeviceFab
        mDeviceListFab = binding.deviceListFab
        mAddDeviceText = binding.addDeviceText
        mDeviceListText = binding.deviceListText
        fabInVisibility()
        mHomeFab?.setOnClickListener {
            mIsAllFabVisible = if (!mIsAllFabVisible!!) {
                mAddDeviceFab?.show()
                mAddDeviceText?.visibility = View.VISIBLE
                mAddDeviceFab?.animate()?.translationY(-resources.getDimension(R.dimen.standard_60))
                mAddDeviceText?.animate()
                    ?.translationY(-resources.getDimension(R.dimen.standard_60))

                mDeviceListFab?.show()
                mDeviceListText?.visibility = View.VISIBLE
                mDeviceListFab?.animate()
                    ?.translationY(-resources.getDimension(R.dimen.standard_120))
                mDeviceListText?.animate()
                    ?.translationY(-resources.getDimension(R.dimen.standard_120))

                mHomeFab!!.extend()
                true
            } else {
                mAddDeviceFab?.hide()
                mAddDeviceText?.visibility = View.GONE
                mAddDeviceFab?.animate()?.translationY(0f)
                mAddDeviceText?.animate()?.translationY(0f)

                mDeviceListFab?.hide()
                mDeviceListText?.visibility = View.GONE
                mDeviceListFab?.animate()?.translationY(0f)
                mAddDeviceText?.animate()?.translationY(0f)
                mHomeFab!!.shrink()
                false
            }
        }
        mAddDeviceFab!!.setOnClickListener {
            val intent = Intent(requireActivity(), HomeActivity::class.java)
            intent.putExtra("Home", "AddDevice")
            startActivity(intent)

        }
        mDeviceListFab!!.setOnClickListener {
            val intent = Intent(requireActivity(), HomeActivity::class.java)
            startActivity(intent)
        }

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

    private fun fabInVisibility() {
        mAddDeviceFab?.visibility = View.GONE
        mDeviceListFab?.visibility = View.GONE
        mAddDeviceText?.visibility = View.GONE
        mDeviceListText?.visibility = View.GONE
        mIsAllFabVisible = false
        mHomeFab?.shrink()
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