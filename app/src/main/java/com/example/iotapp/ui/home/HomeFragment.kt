package com.example.iotapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
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
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        thread {
            while (_binding != null) {
                val formatter = SimpleDateFormat("yyyy/MM/dd  E  hh:mm:ss  a")
                binding.dateWeekTime.text = formatter.format(Date(System.currentTimeMillis()))
                Thread.sleep(1000)
            }
        }


        //測試廷長線開關
        _binding!!.testSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                _binding!!.testSwitch.setText("開")
                thread{
                    val socket = Socket("192.168.0.10",7557)
                    val out = DataOutputStream(socket.getOutputStream())
                    out.writeUTF("on")
                    Log.d("HomeFragment","on")
                    out.close()
                    socket.close()
                    println("finish on")

                }
            } else {
                _binding!!.testSwitch.setText("關")
                thread{
                    val socket = Socket("192.168.0.10",7557)
                    val out = DataOutputStream(socket.getOutputStream())
                    out.writeUTF("off")
                    out.close()
                    socket.close()
                    println("finish off")
                }
            }
        }
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}