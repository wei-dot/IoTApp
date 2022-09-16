package com.iotApp.view.mode

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.iotApp.R
import com.iotApp.api.IotApi
import com.iotApp.databinding.ActivityMainBinding
import com.iotApp.databinding.FragmentMode3NamingBinding
import com.iotApp.model.PostModeKeyDataInfo
import com.iotApp.repository.SessionManager

class ModeSetNamingFragment : Fragment() {
    private var _binding: FragmentMode3NamingBinding? = null
    private val binding get() = _binding!!
    private var modeViewModel: ModeViewModel? = null
    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        modeViewModel = activity?.run {
            ViewModelProvider(this)[ModeViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        _binding = FragmentMode3NamingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(
            "ModeSetNamingFragment",
            "arguments?.getString ${this.arguments?.getString("home_id")}"
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonModeKeyComplete.setOnClickListener {
            IotApi.getFamily(
                requireActivity(),
                ActivityMainBinding.inflate(layoutInflater).profilePage,
                SessionManager(requireActivity())
            )
            if (binding.tilModeKeyName.text?.isNotEmpty() == true) {
                val modeKeyDataId = modeViewModel?.getTplinkSwitch()
                val homeId = SessionManager(requireActivity()).fetchFamilyId()
//                val home_id = "1"
                val modeKeyName = binding.tilModeKeyName.text.toString()
                val acTemperature = modeViewModel?.getAcTemperature()
                val acSwitch = modeViewModel?.getAcSwitch()
                val fanLevel = modeViewModel?.getFanLevel()
                val fanSwitch = modeViewModel?.getFanSwitch()
                val fanSpin = modeViewModel?.getFanSpin()
                if (modeKeyDataId != null && homeId != null && acTemperature != null && acSwitch != null && fanLevel != null && fanSwitch != null && fanSpin != null) {
                    val modeKey = PostModeKeyDataInfo(
                        homeId,
                        modeKeyDataId,
                        modeKeyName,
                        acTemperature,
                        acSwitch,
                        fanLevel,
                        fanSwitch,
                        fanSpin
                    )
                    IotApi.postModeKeyInfo(
                        requireActivity(),
                        SessionManager(requireActivity()), modeKey
                    )
                    activity?.finish()
//                    ModeFragment.DataAdapter().updateData()

//                    Navigation.findNavController(it)
//                        .navigate(R.id.action_navigation_mode_3_naming_to_navigation_mode)
                } else {
                    Log.d("ModeSetNamingFragment", "mode_key_data_id fail")
                }
//                val modeKeyDataInfo = GetModeKeyDataInfo()
            } else {
                Toast.makeText(context, "欄位不得為空", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnBack3.setOnClickListener {
//            activity?.finish()
            Navigation.findNavController(it)
                .navigate(R.id.action_navigation_mode_3_naming_to_navigation_mode_2_ac_set)
        }
    }

}