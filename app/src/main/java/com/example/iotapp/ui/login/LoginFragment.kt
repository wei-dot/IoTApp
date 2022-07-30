package com.example.iotapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.iotapp.MainActivity
import com.example.iotapp.databinding.FragmentLoginBinding
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import java.nio.charset.StandardCharsets.UTF_8
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class LoginFragment : Fragment() {

    private var _binging: FragmentLoginBinding? = null
    private val binding get() = _binging!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binging = FragmentLoginBinding.inflate(inflater, container, false)
        val root = binding.root
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            activity?.finish()
            startActivity(Intent(this.context, MainActivity::class.java))
        }
        var debug : String = ""
        binding.btnSend.setOnClickListener {
            binding.loading?.isVisible = true
            var getAccess : Boolean = false
            val username: String = binding.inputUsername.text.toString()
            var password : String = binding.inputPassword.text.toString()
            val es: ExecutorService = Executors.newCachedThreadPool()
            es.execute{
                val socket = Socket("114.34.88.214", 7558)
                val out = DataOutputStream(socket.getOutputStream())
                val inData = DataInputStream(socket.getInputStream())
                out.flush()
                out.write(username.toByteArray(),0,username.length)
                es.awaitTermination(1, TimeUnit.SECONDS)
                out.write(password.toByteArray(),0,password.length)
                out.flush()
                try {
                    val rev = String(inData.readBytes(), UTF_8)
                    debug = rev
                    Log.d("IoTApi",debug)
                    if (rev == "true" || rev == "tru") {
                        Log.d("IotApi", "pass")
                        getAccess = true
                        out.close()
                        socket.close()

                    } else {
                        Log.d("IotApi", "access denied")
                        out.close()
                        socket.close()
                    }
                } catch (e: Exception) {
                    es.awaitTermination(2, TimeUnit.SECONDS)
                }
                es.shutdown()
            }
            es.shutdown()
            val finished = es.awaitTermination(1, TimeUnit.MINUTES)

            while (true) {
                if (finished) {
                    if (getAccess) {
                        Log.d("IoTApi", "success")
                        binding.loading?.isVisible = false
                        activity?.finish()
                    } else {
                        Log.d("IoTApi", "not success")
                        binding.loading?.isVisible = false
                    }
                    break
                }
            }
        }
    }
}
