package com.iotApp.main.notLogin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.iotApp.AccountActivity
import com.iotApp.databinding.FragmentMainUnloginBinding

class MainNotLoginFragment : Fragment() {
    private var _binding: FragmentMainUnloginBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notLoginViewModel =
            ViewModelProvider(this)[MainNotLoginViewModel::class.java]

        _binding = FragmentMainUnloginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textUnlogin
        notLoginViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(activity, AccountActivity::class.java)
            activity?.finish()
            startActivity(intent)
        }

        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}