package com.example.iotapp.ui.mode

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.iotapp.R
import com.example.iotapp.databinding.FragmentMainModeBinding


class ModeFragment : Fragment() {
    private var _binding: FragmentMainModeBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val ModeViewModel =
            activity?.run { ViewModelProvider(this)[ModeViewModel::class.java] } ?: throw Exception(
                "Invalid Activity"
            )
        _binding = FragmentMainModeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textMode
//        modeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Log.d("modeFragment",IotApi.getModeKeyInfo(requireActivity(),SessionManager(requireActivity())).toString())
        try {
            _binding?.buttonModeKey1?.setOnClickListener {
//                Handler(Looper.getMainLooper()).postDelayed({
////                findNavController().navigate(R.id.action_navigation_mode_to_navigation_mode_1_switch_set)
//                },2000)
                Navigation.findNavController(it)
                    .navigate(R.id.action_navigation_mode_to_navigation_mode_1_switch_set)

            }
        }catch (e: Exception) {
            Log.d("modeFragment",e.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setCardViewEvent() {
//        binding.cardViewMode1?.setOnClickListener(CardViewButtonListener)
    }

//    private fun modeKeyChangeEvent(mode: Int) {
//        var item = LayoutInflater.from(activity).inflate(R.layout.change_mode_key_name_layout, null)
//        AlertDialog.Builder(activity)
//            .setTitle("設定組合鍵${mode}名稱")
//            .setView(item)
//            .setPositiveButton(R.string.confirm) { _, _ ->
//                val editText = item.findViewById(R.id.edit_text) as EditText
//                val name = editText.text.toString()
//                if (name.isEmpty()) {
//                    Toast.makeText(activity, "請輸入名稱", Toast.LENGTH_SHORT).show()
//                } else {
//                    when (mode) {
//                    }
//                }
//
//            }
//    }
}