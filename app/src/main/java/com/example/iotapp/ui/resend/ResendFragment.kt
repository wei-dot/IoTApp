package com.example.iotapp.ui.resend

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.iotapp.R
import com.example.iotapp.api.IotApi
import com.example.iotapp.api.SendEmail
import com.example.iotapp.databinding.FragmentResendBinding
import okhttp3.internal.format

class ResendFragment : Fragment() {
    private var _binding: FragmentResendBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResendBinding.inflate(inflater, container, false)
        setFragmentResultListener("requestKey") { _, bundle ->
            val temp = bundle.getString("bundleKey").toString()
            Log.d("Test", temp)
            binding.resend.let {
                it.isEnabled = false
                val countDownTimer = object : CountDownTimer(30 * 1000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        if (_binding != null) {
                            it.text = format("%d 秒後重新寄送", millisUntilFinished / 1000)
                        } else {
                            cancel()
                        }
                    }

                    override fun onFinish() {
                        if (_binding != null) {
                            it.text = "重新寄送"
                            it.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.color_14b2d4
                                )
                            )
                            it.isEnabled = true
                        } else {
                            cancel()
                        }
                    }
                }
                countDownTimer.start()
                it.setOnClickListener { _ ->
                    IotApi.resendActivation(SendEmail(temp), activity)
                    countDownTimer.start()
                    it.isEnabled = false
                    it.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_AAAAAA))
                }
            }
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_resendFragment_to_loginFragment)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}