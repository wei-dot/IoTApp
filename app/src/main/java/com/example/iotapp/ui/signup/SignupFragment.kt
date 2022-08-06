package com.example.iotapp.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.RegexUtils
import com.example.iotapp.MainActivity
import com.example.iotapp.api.IotApi
import com.example.iotapp.api.UserInfo
import com.example.iotapp.databinding.FragmentAccountSignupBinding

class SignupFragment : Fragment() {
    private var _binging: FragmentAccountSignupBinding? = null
    private val binding get() = _binging!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binging = FragmentAccountSignupBinding.inflate(inflater, container, false)
        val root = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            activity?.finish()
            startActivity(Intent(this.context, MainActivity::class.java))
        }
        binding.btnSend.setOnClickListener {
            var msg = ""
            val username: String = binding.tilUsername?.editText?.text.toString()
            val password = binding.tilPassword?.editText?.text.toString()
            val password2 = binding.tilPasswordConfirm?.editText?.text.toString()
            if (password != password2) {
                msg = "輸入密碼不相符"
            }
            val email: String = binding.tilEmail?.editText?.text.toString()
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
                IotApi().signup(user, activity, binding)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binging = null

    }

}