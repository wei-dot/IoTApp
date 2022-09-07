package com.iotApp.account.signup

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.RegexUtils
import com.iotApp.MainActivity
import com.iotApp.R
import com.iotApp.api.IotApi
import com.iotApp.databinding.FragmentAccountSignupBinding
import com.iotApp.model.UserInfo

class SignupFragment : Fragment() {
    private var _binging: FragmentAccountSignupBinding? = null
    private val binding get() = _binging!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binging = FragmentAccountSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            activity?.finish()
            startActivity(Intent(this.context, MainActivity::class.java))
        }
        binding.btnSend.setOnClickListener {
            var msg = ""
            val username: String = binding.tilUsername.editText?.text.toString()
            val password = binding.tilPassword.editText?.text.toString()
            val password2 = binding.tilPasswordConfirm.editText?.text.toString()
            if (password != password2) {
                msg = "輸入密碼不相符"
            }
            val email: String = binding.tilEmail.editText?.text.toString()
            if (username.isEmpty() && msg.isEmpty()) {
                msg = "使用者名稱不得為空"
            } else if (password.isEmpty() && msg.isEmpty()) {
                msg = "使用者密碼不得為空"
            } else if ((!RegexUtils.isEmail(email)) && msg.isEmpty()) {
                msg = if (email.isEmpty()) "信箱不得為空" else "信箱格式錯誤"
            }
            if (msg.isNotEmpty()) {
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
            } else {
                val user = UserInfo(username, password, password2, "", "", email)
                binding.loading.isVisible = true
                IotApi.signup(user, activity, binding)
                setFragmentResult("requestKey", bundleOf("bundleKey" to email))
                Handler(Looper.getMainLooper()).postDelayed({
                    // Your Code
                    findNavController().navigate(R.id.action_signupFragment_to_resendFragment)
                }, 2000)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binging = null

    }

}