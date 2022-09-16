package com.iotApp.view.mode

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.iotApp.R
import com.iotApp.databinding.FragmentMode2AcSettingBinding


class ModeSetAcFragment : Fragment() {
    private var _binding: FragmentMode2AcSettingBinding? = null
    private val binding get() = _binding!!
    private var modeViewModel: ModeViewModel? = null
    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMode2AcSettingBinding.inflate(inflater, container, false)
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
            modeViewModel?.setFanLevel(binding.fanLevelNumberPicker.value)
            modeViewModel?.setFanSwitch(binding.modeKeyFanSwitch.isChecked)
            modeViewModel?.setFanSpin(binding.modeKeyFanSpin.isChecked)
            Navigation.findNavController(it)
                .navigate(R.id.action_navigation_mode_2_ac_set_to_navigation_mode_3_naming)

        }
        _binding?.btnBack3?.setOnClickListener {
//            activity?.finish()
            Navigation.findNavController(it)
                .navigate(R.id.action_navigation_mode_2_ac_set_to_navigation_mode_1_switch_set)

        }

    }

}