package com.example.iotapp.ui.notLogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.iotapp.databinding.FragmentNotLoginBinding
import com.example.iotapp.ui.log.LogViewModel

class notLoginFragment : Fragment() {
    private var _binding: FragmentNotLoginBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notLoginViewModel =
            ViewModelProvider(this)[notLoginViewModel::class.java]

        _binding = FragmentNotLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView? = binding.textNotLogin
        notLoginViewModel.text.observe(viewLifecycleOwner) {
            textView?.text = it
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