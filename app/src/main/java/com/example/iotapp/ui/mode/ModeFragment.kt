package com.example.iotapp.ui.mode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.iotapp.databinding.FragmentModeBinding

class ModeFragment : Fragment() {
    private var _binding: FragmentModeBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val modeViewModel =
            ViewModelProvider(this)[ModeViewModel::class.java]

        _binding = FragmentModeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMode
        modeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.buttonSecond.setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//
//
//        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}