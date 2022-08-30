package com.iotApp.main.log

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.iotApp.databinding.FragmentMainLogBinding

class LogFragment : Fragment() {
    private var _binding: FragmentMainLogBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val logViewModel =
            ViewModelProvider(this)[LogViewModel::class.java]

        _binding = FragmentMainLogBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textLog
        logViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}