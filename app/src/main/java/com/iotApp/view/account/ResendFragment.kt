package com.iotApp.view.account

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.iotApp.R
import com.iotApp.databinding.FragmentAccountResendBinding
import com.iotApp.viewmodel.AccountViewModel
import com.iotApp.viewmodel.ViewModelFactory
import okhttp3.internal.format

class ResendFragment : Fragment() {
    private lateinit var viewModel: AccountViewModel

    private var _binding: FragmentAccountResendBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountResendBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this@ResendFragment,
            ViewModelFactory()
        )[AccountViewModel::class.java]
        setFragmentResultListener("requestKey") { _, bundle ->
            val temp = bundle.getString("bundleKey").toString()
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
                    viewModel.resendEmail(temp)
                    countDownTimer.start()
                    it.isEnabled = false
                    it.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_AAAAAA))
                }
            }
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_resendFragment_to_loginFragment)
        }
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        findNavController().navigate(R.id.action_resendFragment_to_loginFragment)
                    }
                }
            )

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}