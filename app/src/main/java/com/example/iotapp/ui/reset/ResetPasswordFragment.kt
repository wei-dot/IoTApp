package com.example.iotapp.ui.reset

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.iotapp.databinding.FragmentResetBinding

class ResetPasswordFragment : Fragment() {

    private var _binging: FragmentResetBinding? = null
    private val binding get() = _binging!!

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binging = FragmentResetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            activity?.finish()
        }
        binding.btnSend.setOnClickListener{
            activity?.finish()
        }
    }
}