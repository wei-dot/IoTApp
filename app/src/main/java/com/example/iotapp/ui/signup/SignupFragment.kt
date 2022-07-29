package com.example.iotapp.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.iotapp.MainActivity
import com.example.iotapp.api.*
import com.example.iotapp.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {
    private var _binging: FragmentSignupBinding? = null
    private val binding get() = _binging!!
    private var myAPIService: MyAPIService? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binging = FragmentSignupBinding.inflate(inflater, container, false)
        val root = binding.root
        myAPIService = RetrofitManager.getInstance().create(MyAPIService::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            activity?.finish()
            startActivity(Intent(this.context, MainActivity::class.java))
        }
        binding.btnSend.setOnClickListener {
            binding.loading.isVisible = true
            val username: String = binding.inputUsername.text.toString()
            var password = ""
            if (binding.inputPassword.text.contentEquals(binding.inputConfirmPassword.text)) {
                password = binding.inputPassword.text.toString()
            }
            val email: String = binding.inputEmail.text.toString()
            val user = UserInfo(username, password, "", "", email)
            IotApi().postInfo(user, activity, binding)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binging = null

    }

}