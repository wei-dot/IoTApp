package com.iotApp.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.iotApp.R
import com.iotApp.databinding.FragmentHomeAddDeviceBinding
import com.iotApp.model.AddDevice
import com.iotApp.repository.SessionManager
import java.util.*

class HomeAddDeviceFragment : Fragment() {
    private lateinit var viewModel: HomeAddDeviceViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DeviceAdapter
    private lateinit var mData: ArrayList<String>
    private var _binding: FragmentHomeAddDeviceBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeAddDeviceBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this@HomeAddDeviceFragment,
            ViewModelFactory()
        )[HomeAddDeviceViewModel::class.java]
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            androidx.recyclerview.widget.DividerItemDecoration(
                context,
                androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
            )
        )
        binding.returnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        val items = listOf("感測器", "家用電器")
        val categoryAdapter = ArrayAdapter(requireContext(), R.layout.category_list_item, items)
        (binding.menu.editText as? AutoCompleteTextView)?.setAdapter(categoryAdapter)
        binding.filledExposedDropdown.setText(items[0], false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mData =
            ArrayList(listOf("DHT11溫溼度感測器", "MQ7一氧化碳感測器", "HC-SR501人體感測器"))
        adapter = DeviceAdapter(mData)
        recyclerView.adapter = adapter
        binding.filledExposedDropdown.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, _, id ->
                when (id) {
                    0L -> {
                        mData =
                            ArrayList(listOf("DHT11溫溼度感測器", "MQ7一氧化碳感測器", "HC-SR501人體感測器"))

                        adapter.changeData(mData)
                    }
                    1L -> {
                        mData =
                            ArrayList(listOf("TpLink延長線", "電扇", "冷氣"))
                        adapter.changeData(mData)
                    }

                }
                adapter.notifyDataSetChanged()
            }
        binding.add.setOnClickListener {
            try {

                val familyId = SessionManager(requireContext()).fetchFamilyId()
                val userId = SessionManager(requireContext()).fetchUserInfo()?.username
                val deviceId = UUID.randomUUID().toString()
                if (familyId != null) {
                    val alertDialog = MaterialAlertDialogBuilder(
                        requireContext(),
                        R.style.MaterialAlertDialog_Rounded
                    ).setTitle("配對裝置")
                        .setMessage("確定要配對\n${mData[adapter.selectedPosition]}?")
                        .setBackground(
                            ContextCompat.getDrawable(
                                requireActivity(),
                                R.drawable.background_popup
                            )
                        )
                        .create()

                    val viewDialog = layoutInflater.inflate(R.layout.pairing_dialog, null)
                    alertDialog.setView(viewDialog)
                    val cancel = viewDialog.findViewById<MaterialButton>(R.id.cancel)
                    val confirm = viewDialog.findViewById<MaterialButton>(R.id.ok)
                    val ssid = viewDialog.findViewById<TextInputEditText>(R.id.et_ssid)
                    val password = viewDialog.findViewById<TextInputEditText>(R.id.et_password)


                    alertDialog.show()
                    cancel.setOnClickListener {
                        alertDialog.dismiss()
                        Toast.makeText(context, "取消配對", Toast.LENGTH_SHORT).show()
                    }
                    confirm.setOnClickListener {
                        val ssidText = ssid.text.toString()
                        val passwordText = password.text.toString()
                        if (ssidText.isEmpty() || passwordText.isEmpty()) {
                            Toast.makeText(context, "請輸入完整資訊", Toast.LENGTH_SHORT).show()
                        } else {
                            alertDialog.dismiss()
                            viewModel.pairDevice(
                                requireContext(),
                                ssidText,
                                passwordText,
                                "$familyId$deviceId$userId"
                            )
                            val device = AddDevice(id=deviceId,"我的設備", "DHT11", home_id = familyId)
                            viewModel.addDevice(
                                "Token ${SessionManager(requireContext()).fetchAuthToken()}",
                                device
                            )
                            Toast.makeText(context, "配對成功", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    AlertDialog.Builder(requireContext())
                        .setTitle("錯誤")
                        .setMessage("未加入家庭")
                        .setPositiveButton("確定") { _, _ -> {} }.create().show()
                }


            } catch (e: Exception) {
                AlertDialog.Builder(requireContext())
                    .setTitle("錯誤")
                    .setMessage("未選擇設備")
                    .setPositiveButton("確定") { _, _ -> {} }.create().show()
            }
        }

    }

}