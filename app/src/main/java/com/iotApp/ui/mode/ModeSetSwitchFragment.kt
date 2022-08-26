package com.iotApp.ui.mode

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.iotApp.MainActivity
import com.iotApp.R
import com.iotApp.databinding.FragmentMainMode1TplinkSwitchSettingBinding


class ModeSetSwitchFragment : Fragment() {
    private var _binding: FragmentMainMode1TplinkSwitchSettingBinding? = null
    private val binding get() = _binding!!
    var modeViewModel: ModeViewModel? = null
    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMode1TplinkSwitchSettingBinding.inflate(inflater, container, false)
        modeViewModel = activity?.run {
            ViewModelProvider(this)[ModeViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ModeSetSwitchFragment", "onCreate ModeSetSwitchFragment")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.modeKeyTplinkSwitch1?.text = "開關1 開"
        _binding?.modeKeyTplinkSwitch2?.text = "開關2 開"
        _binding?.modeKeyTplinkSwitch3?.text = "開關3 開"
        _binding?.modeKeyTplinkSwitch4?.text = "開關4 開"
        _binding?.modeKeyTplinkSwitch5?.text = "開關5 開"
        _binding?.modeKeyTplinkSwitch6?.text = "開關6 開"
        var switchKey= ""

        _binding?.buttonModeKeyNextStep1?.setOnClickListener {
            Log.d("setOnClickListener", "setOnClickListener")
            switchKey += if (_binding?.modeKeyTplinkSwitch1?.isChecked!!) "1" else "0"
            switchKey += if (_binding?.modeKeyTplinkSwitch2?.isChecked!!) "1" else "0"
            switchKey += if (_binding?.modeKeyTplinkSwitch3?.isChecked!!) "1" else "0"
            switchKey += if (_binding?.modeKeyTplinkSwitch4?.isChecked!!) "1" else "0"
            switchKey += if (_binding?.modeKeyTplinkSwitch5?.isChecked!!) "1" else "0"
            switchKey += if (_binding?.modeKeyTplinkSwitch6?.isChecked!!) "1" else "0"
            Log.d("setOnClickListener", "switchKey: $switchKey")
            modeViewModel?.setTplinkSwitch(switchKey)
//            val intent = Intent(activity, MainActivity::class.java)
//            intent.putExtra("switchKey", switchKey)
//            requireActivity().startActivity(intent)
            Navigation.findNavController(it)
                .navigate(R.id.action_navigation_mode_1_switch_set_to_navigation_mode_2_ac_set)
        }
    }

}