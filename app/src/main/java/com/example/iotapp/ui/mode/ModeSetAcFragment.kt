package com.example.iotapp.ui.mode

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.iotapp.R
import com.example.iotapp.databinding.FragmentMainMode2AcSettingBinding

class ModeSetAcFragment : Fragment() {
    private var _binding: FragmentMainMode2AcSettingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: android.os.Bundle?
    ): View {
        _binding = FragmentMainMode2AcSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FragmentMainMode2AcSettingBinding", "onCreate FragmentMainMode2AcSettingBinding")
        _binding?.modeKeyTplinkSwitch1?.text = "開"
        _binding?.modeKeyTplinkSwitch1?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                _binding?.modeKeyTplinkSwitch1?.text = "開"
            } else {
                _binding?.modeKeyTplinkSwitch1?.text = "關"
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding?.buttonModeKeyNextStep2?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_navigation_mode_2_ac_set_to_navigation_mode_3_naming)
        }
    }

}