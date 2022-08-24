package com.example.iotapp.ui.mode

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.iotapp.R
import com.example.iotapp.databinding.FragmentMainMode2AcSettingBinding
import com.example.iotapp.ui.log.LogViewModel


class ModeSetAcFragment : Fragment() {
    private var _binding: FragmentMainMode2AcSettingBinding? = null
    private val binding get() = _binding!!
    var modeViewModel: ModeViewModel? = null
    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: android.os.Bundle?
    ): View {
        _binding = FragmentMainMode2AcSettingBinding.inflate(inflater, container, false)
        modeViewModel = activity?.run {
            ViewModelProvider(this)[ModeViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FragmentMainMode2AcSettingBinding", "onCreate FragmentMainMode2AcSettingBinding")
        _binding?.modeKeyAcSwitch?.text = "開"
        _binding?.modeKeyAcSwitch?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                _binding?.modeKeyAcSwitch?.text = "開"
            } else {
                _binding?.modeKeyAcSwitch?.text = "關"
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        _binding?.buttonModeKeyNextStep2?.setOnClickListener {
//            Log.d("FragmentMainMode2AcSettingBinding", modeViewModel?.getTplinkSwitch()?.value.toString())
            modeViewModel?.setAcTemperature(binding.acTemperatureNumberPicker.value)
            modeViewModel?.setAcSwitch(binding.modeKeyAcSwitch.isChecked)
            Navigation.findNavController(it)
                .navigate(R.id.action_navigation_mode_2_ac_set_to_navigation_mode_3_naming)

        }

    }

}