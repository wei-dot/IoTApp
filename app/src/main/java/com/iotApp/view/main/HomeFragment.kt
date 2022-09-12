package com.iotApp.view.main

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ThreadUtils.runOnUiThread
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.iotApp.R
import com.iotApp.Constants
import com.iotApp.api.WsListener
import com.iotApp.databinding.FragmentMainHomeBinding
import com.iotApp.view.HomeActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

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
    private val host: String = "192.168.0.13:8000"
    private val mWbSocketUrl = "ws://" + host + Constants.Power_Strip_URL
    private lateinit var mClient: OkHttpClient
    private lateinit var request: Request
    private lateinit var mWebSocket: WebSocket

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


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

        thread {
            while (true) {
                try {
                    val url =
                        URL("https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-C0032-001?Authorization=CWB-FF475748-282F-4E9F-81CD-89D15DE20B89&format=JSON&locationName=%E8%87%BA%E5%8C%97%E5%B8%82&elementName=&sort=time")
                    val urlConnection = url.openConnection() as HttpURLConnection
                    val inStream = BufferedInputStream(urlConnection.inputStream)
                    val reader = BufferedReader(InputStreamReader(inStream))
                    val result = StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        result.append(line)
                    }
                    val json = JSONObject(result.toString())
                    val records = json.getJSONObject("records")
                    val location = records.getJSONArray("location")
                    val weatherElement = location.getJSONObject(0).getJSONArray("weatherElement")
                    val wx = weatherElement.getJSONObject(0).getJSONArray("time")
                    val wxParameter = wx.getJSONObject(0).getJSONObject("parameter")
                    val wxTime = wx.getJSONObject(0).getString("startTime")
                    val wxParameterName = wxParameter.getString("parameterName")
                    val wxParameterValue = wxParameter.getString("parameterValue")
                    val pop = weatherElement.getJSONObject(1).getJSONArray("time")
                    val popParameter = pop.getJSONObject(0).getJSONObject("parameter")
                    val popParameterName = popParameter.getString("parameterName")
                    val minT = weatherElement.getJSONObject(2).getJSONArray("time")
                    val minTParameter = minT.getJSONObject(0).getJSONObject("parameter")
                    val minTParameterName = minTParameter.getString("parameterName")
                    val maxT = weatherElement.getJSONObject(4).getJSONArray("time")
                    val maxTParameter = maxT.getJSONObject(0).getJSONObject("parameter")
                    val maxTParameterName = maxTParameter.getString("parameterName")

                    runOnUiThread {
                        val dayNight = if (LocalDateTime.now().hour > 18 || LocalDateTime.now().hour < 6) "night" else "day"
                        when((minTParameterName.toInt() + maxTParameterName.toInt()) / 2){
                            in -999..15 -> {binding.MaxT.setTextColor(ContextCompat.getColor(requireContext(),R.color.color_86ccdb))
                                binding.MinT.setTextColor(ContextCompat.getColor(requireContext(),R.color.color_86ccdb))}
                            in 16..30 -> {binding.MaxT.setTextColor(ContextCompat.getColor(requireContext(),R.color.color_c07a27))
                                binding.MinT.setTextColor(ContextCompat.getColor(requireContext(),R.color.color_c07a27))}
                            in 31..40 -> {binding.MaxT.setTextColor(ContextCompat.getColor(requireContext(),R.color.color_df8888))
                                binding.MinT.setTextColor(ContextCompat.getColor(requireContext(),R.color.color_df8888))}
                            else -> binding.MaxT.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
                        }
                        binding.MinT.text = "${minTParameterName}°C"
                        binding.MaxT.text = "${maxTParameterName}°C"
                        binding.icWeather.setImageDrawable(
                            resources.assets.open("weather/${dayNight}/${wxParameterValue}.png").use {
                                Drawable.createFromStream(it, null)
                            }
                        )
                        binding.wx.text = wxParameterName
                        binding.pop.text = "降雨機率:${popParameterName}%"
                        binding.textUpdateTime.text = "資訊時間:${wxTime}"
                    }
                    Log.d("weather", "parameterName: $wxParameterName")

                    Thread.sleep(1000 * 60 * 60)
                } catch (e: Exception) {
                    e.printStackTrace()
                } catch (e: Exception) {
                    Log.d("weather", "error")
                }
                Thread.sleep(10000)
            }
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