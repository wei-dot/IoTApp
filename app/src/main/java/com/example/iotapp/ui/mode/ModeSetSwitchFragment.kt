package com.example.iotapp.ui.mode

import android.R.id.message
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.iotapp.MainActivity
import com.example.iotapp.R
import com.example.iotapp.databinding.FragmentMainMode1TplinkSwitchSettingBinding


class ModeSetSwitchFragment : Fragment() {
    private var _binding: FragmentMainMode1TplinkSwitchSettingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: android.os.Bundle?
    ): View {
        _binding = FragmentMainMode1TplinkSwitchSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ModeSetSwitchFragment", "onCreate ModeSetSwitchFragment")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.modeKeyTplinkSwitch1?.setText("開關1 開")
        _binding?.modeKeyTplinkSwitch2?.setText("開關2 開")
        _binding?.modeKeyTplinkSwitch3?.setText("開關3 開")
        _binding?.modeKeyTplinkSwitch4?.setText("開關4 開")
        _binding?.modeKeyTplinkSwitch5?.setText("開關5 開")
        _binding?.modeKeyTplinkSwitch6?.setText("開關6 開")
        var switchKey: String = ""

        _binding?.buttonModeKeyNextStep1?.setOnClickListener {
            Log.d("setOnClickListener", "setOnClickListener")
            if (_binding?.modeKeyTplinkSwitch1?.isChecked!!) switchKey += "1" else switchKey += "0"
            if (_binding?.modeKeyTplinkSwitch2?.isChecked!!) switchKey += "1" else switchKey += "0"
            if (_binding?.modeKeyTplinkSwitch3?.isChecked!!) switchKey += "1" else switchKey += "0"
            if (_binding?.modeKeyTplinkSwitch4?.isChecked!!) switchKey += "1" else switchKey += "0"
            if (_binding?.modeKeyTplinkSwitch5?.isChecked!!) switchKey += "1" else switchKey += "0"
            if (_binding?.modeKeyTplinkSwitch6?.isChecked!!) switchKey += "1" else switchKey += "0"
            Log.d("setOnClickListener", "switchKey: $switchKey")
            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra("switchKey", switchKey)
            requireActivity().startActivity(intent)
            Navigation.findNavController(it)
                .navigate(R.id.action_navigation_mode_1_switch_set_to_navigation_mode_2_ac_set)
        }
    }

}