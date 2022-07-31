package com.example.iotapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.iotapp.databinding.FragmentHomeBinding
import java.io.DataOutputStream
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)



        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        thread {
            while (_binding != null) {
                val formatter = SimpleDateFormat("yyyy/MM/dd  E  hh:mm:ss  a")
                binding.dateWeekTime.text = formatter.format(Date(System.currentTimeMillis()))
                Thread.sleep(1000)
            }
        }
        //預設全部開關關閉
        binding.tplinkSwitch1.isChecked = false
        binding.tplinkSwitch2.isChecked = false
        binding.tplinkSwitch3.isChecked = false
        binding.tplinkSwitch4.isChecked = false
        binding.tplinkSwitch5.isChecked = false
        binding.tplinkSwitch6.isChecked = false


        //測試廷長線開關
        _binding!!.tplinkSwitch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                _binding!!.tplinkSwitch1.text = "開"
                thread{
                    val socket = Socket("192.168.0.10", 7559)
                    val out = DataOutputStream(socket.getOutputStream())
                    out.writeUTF("1on")
                    Log.d("HomeFragment","on")
                    out.close()
                    socket.close()
                    println("finish on")

                }
            } else {
                _binding!!.tplinkSwitch1.text = "關"
                thread{
                    val socket = Socket("192.168.0.10",7559)
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
                _binding!!.tplinkSwitch2.text = "開"
                thread{
                    val socket = Socket("192.168.0.10", 7559)
                    val out = DataOutputStream(socket.getOutputStream())
                    out.writeUTF("2on")
                    Log.d("HomeFragment","on")
                    out.close()
                    socket.close()
                    println("finish on")

                }
            } else {
                _binding!!.tplinkSwitch2.text = "關"
                thread{
                    val socket = Socket("192.168.0.10",7559)
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
                _binding!!.tplinkSwitch3.text = "開"
                thread{
                    val socket = Socket("192.168.0.10", 7559)
                    val out = DataOutputStream(socket.getOutputStream())
                    out.writeUTF("3on")
                    Log.d("HomeFragment","on")
                    out.close()
                    socket.close()
                    println("finish on")

                }
            } else {
                _binding!!.tplinkSwitch3.text = "關"
                thread{
                    val socket = Socket("192.168.0.10",7559)
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
                _binding!!.tplinkSwitch4.text = "開"
                thread{
                    val socket = Socket("192.168.0.10", 7559)
                    val out = DataOutputStream(socket.getOutputStream())
                    out.writeUTF("4on")
                    Log.d("HomeFragment","on")
                    out.close()
                    socket.close()
                    println("finish on")

                }
            } else {
                _binding!!.tplinkSwitch4.text = "關"
                thread{
                    val socket = Socket("192.168.0.10",7559)
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
                _binding!!.tplinkSwitch5.text = "開"
                thread {
                    val socket = Socket("192.168.0.10", 7559)
                    val out = DataOutputStream(socket.getOutputStream())
                    out.writeUTF("5on")
                    Log.d("HomeFragment", "on")
                    out.close()
                    socket.close()
                    println("finish on")

                }
            } else {
                _binding!!.tplinkSwitch5.text = "關"
                thread {
                    val socket = Socket("192.168.0.10", 7559)
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
                _binding!!.tplinkSwitch6.text = "開"
                thread{
                    val socket = Socket("192.168.0.10", 7559)
                    val out = DataOutputStream(socket.getOutputStream())
                    out.writeUTF("6on")
                    Log.d("HomeFragment","on")
                    out.close()
                    socket.close()
                    println("finish on")

                }
            } else {
                _binding!!.tplinkSwitch6.text = "關"
                thread{
                    val socket = Socket("192.168.0.10",7559)
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