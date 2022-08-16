package com.example.iotapp.ui.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.ThreadUtils.runOnUiThread
import com.example.iotapp.R
import com.example.iotapp.databinding.FragmentMainHomeBinding
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import java.nio.charset.StandardCharsets.UTF_8
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentMainHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val host: String = "192.168.0.15"

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
        thread {
            while (_binding != null) {
                val formatter = SimpleDateFormat("yyyy/MM/dd  E  hh:mm:ss  a", Locale.CHINESE)
                binding.dateWeekTime.text = formatter.format(Date(System.currentTimeMillis()))
                Thread.sleep(1000)
            }
        }
        //設定全部開關文字
        binding.tplinkSwitch1.setText("開關1 開")
        binding.tplinkSwitch2.setText("開關2 開")
        binding.tplinkSwitch3.setText("開關3 開")
        binding.tplinkSwitch4.setText("開關4 開")
        binding.tplinkSwitch5.setText("開關5 開")
        binding.tplinkSwitch6.setText("開關6 開")


    thread {
            while (true) {
                Thread.sleep(5000)
                Log.d("thread", "1")
                try {
                    Log.d("thread", "2")
                    val socket = Socket(host, 7560)
                    val out = DataOutputStream(socket.getOutputStream())
                    out.write("ok".toByteArray(), 0, "ok".length)
                    val inp = DataInputStream(socket.getInputStream())
                    Thread.sleep(1000)
                    Log.d("thread", "3")
                    val rev = String(inp.readBytes(), UTF_8)
                    Log.d("thread", rev)
                    out.close()
                    socket.close()
                    rev[0].javaClass.kotlin.qualifiedName?.let {
                        Log.d(
                            "thread type of rev",
                            it
                        )
                    }

                    runOnUiThread {
                        binding.tplinkSwitch1.isChecked = rev[0] == '1'
                        if(rev[0] == '1') binding.imageViewPowerPlugs1?.setImageResource(R.drawable.power_plugs_logo_on)
                        else binding.imageViewPowerPlugs1?.setImageResource(R.drawable.power_plugs_logo_off)
                        binding.tplinkSwitch2.isChecked = rev[1] == '1'
                        if(rev[1] == '1') binding.imageViewPowerPlugs2?.setImageResource(R.drawable.power_plugs_logo_on)
                        else binding.imageViewPowerPlugs2?.setImageResource(R.drawable.power_plugs_logo_off)
                        binding.tplinkSwitch3.isChecked = rev[2] == '1'
                        if(rev[2] == '1') binding.imageViewPowerPlugs3?.setImageResource(R.drawable.power_plugs_logo_on)
                        else binding.imageViewPowerPlugs3?.setImageResource(R.drawable.power_plugs_logo_off)
                        binding.tplinkSwitch4.isChecked = rev[3] == '1'
                        if(rev[3] == '1') binding.imageViewPowerPlugs4?.setImageResource(R.drawable.power_plugs_logo_on)
                        else binding.imageViewPowerPlugs4?.setImageResource(R.drawable.power_plugs_logo_off)
                        binding.tplinkSwitch5.isChecked = rev[4] == '1'
                        if(rev[4] == '1') binding.imageViewPowerPlugs5?.setImageResource(R.drawable.power_plugs_logo_on)
                        else binding.imageViewPowerPlugs5?.setImageResource(R.drawable.power_plugs_logo_off)
                        binding.tplinkSwitch6.isChecked = rev[5] == '1'
                        if(rev[5] == '1') binding.imageViewPowerPlugs6?.setImageResource(R.drawable.power_plugs_logo_on)
                        else binding.imageViewPowerPlugs6?.setImageResource(R.drawable.power_plugs_logo_off)
                        Log.d("thread", "after switch 6")

                    }
                } catch (e: Exception) {
                    Log.d("thread", e.toString())
                    print(e)
                }
            }

        }

        //測試廷長線開關
        _binding!!.tplinkSwitch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                _binding!!.tplinkSwitch1.text = "開關1 開"
                thread {
                    val socket = Socket(host, 7559)
                    val out = DataOutputStream(socket.getOutputStream())
                    out.writeUTF("1on")
                    Log.d("HomeFragment", "on")
                    out.close()
                    socket.close()
                    println("finish on")

                }
            } else {
                _binding!!.tplinkSwitch1.text = "開關1 關"
                thread {
                    val socket = Socket(host, 7559)
                    val out = DataOutputStream(socket.getOutputStream())
                    out.writeUTF("1off")
                    out.close()
                    socket.close()
                    println("finish off")
                }
            }
        }
        _binding!!.tplinkSwitch2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                _binding!!.tplinkSwitch2.text = "開關2 開"
                thread {
                    val socket = Socket(host, 7559)
                    val out = DataOutputStream(socket.getOutputStream())
                    out.writeUTF("2on")
                    Log.d("HomeFragment", "on")
                    out.close()
                    socket.close()
                    println("finish on")

                }
            } else {
                _binding!!.tplinkSwitch2.text = "開關2 關"
                thread {
                    val socket = Socket(host, 7559)
                    val out = DataOutputStream(socket.getOutputStream())
                    out.writeUTF("2off")
                    out.close()
                    socket.close()
                    println("finish off")
                }
            }
        }

        _binding!!.tplinkSwitch3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                _binding!!.tplinkSwitch3.text = "開關3 開"
                thread {
                    val socket = Socket(host, 7559)
                    val out = DataOutputStream(socket.getOutputStream())
                    out.writeUTF("3on")
                    Log.d("HomeFragment", "on")
                    out.close()
                    socket.close()
                    println("finish on")

                }
            } else {
                _binding!!.tplinkSwitch3.text = "開關3 關"
                thread {
                    val socket = Socket(host, 7559)
                    val out = DataOutputStream(socket.getOutputStream())
                    out.writeUTF("3off")
                    out.close()
                    socket.close()
                    println("finish off")
                }
            }
        }

        _binding!!.tplinkSwitch4.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                _binding!!.tplinkSwitch4.text = "開關4 開"
                thread {
                    val socket = Socket(host, 7559)
                    val out = DataOutputStream(socket.getOutputStream())
                    out.writeUTF("4on")
                    Log.d("HomeFragment", "on")
                    out.close()
                    socket.close()
                    println("finish on")

                }
            } else {
                _binding!!.tplinkSwitch4.text = "開關4 關"
                thread {
                    val socket = Socket(host, 7559)
                    val out = DataOutputStream(socket.getOutputStream())
                    out.writeUTF("4off")
                    out.close()
                    socket.close()
                    println("finish off")
                }
            }
        }

        _binding!!.tplinkSwitch5.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                _binding!!.tplinkSwitch5.text = "開關5 開"
                thread {
                    val socket = Socket(host, 7559)
                    val out = DataOutputStream(socket.getOutputStream())
                    out.writeUTF("5on")
                    Log.d("HomeFragment", "on")
                    out.close()
                    socket.close()
                    println("finish on")

                }
            } else {
                _binding!!.tplinkSwitch5.text = "開關5 關"
                thread {
                    val socket = Socket(host, 7559)
                    val out = DataOutputStream(socket.getOutputStream())
                    out.writeUTF("5off")
                    out.close()
                    socket.close()
                    println("finish off")
                }
            }
        }

        _binding!!.tplinkSwitch6.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                _binding!!.tplinkSwitch6.text = "開關6 開"
                thread {
                    val socket = Socket(host, 7559)
                    val out = DataOutputStream(socket.getOutputStream())
                    out.writeUTF("6on")
                    Log.d("HomeFragment", "on")
                    out.close()
                    socket.close()
                    println("finish on")

                }
            } else {
                _binding!!.tplinkSwitch6.text = "開關6 關"
                thread {
                    val socket = Socket(host, 7559)
                    val out = DataOutputStream(socket.getOutputStream())
                    out.writeUTF("6off")
                    out.close()
                    socket.close()
                    println("finish off")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}