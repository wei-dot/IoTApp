package com.iotApp.main.mode

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iotApp.ModeActivity
import com.iotApp.R
import com.iotApp.api.GetModeKeyDataInfo
import com.iotApp.api.IotApi
import com.iotApp.api.SessionManager
import com.iotApp.databinding.FragmentMainModeBinding


class ModeFragment : Fragment() {
    private var _binding: FragmentMainModeBinding? = null
    private var dataList: RecyclerView? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainModeBinding.inflate(inflater, container, false)
        IotApi.getModeKeyInfo(requireActivity(), SessionManager(requireActivity()))
        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        dataList = binding.recyclerViewModeKeySet
        if (dataList != null) {
            dataList!!.layoutManager = layoutManager
//            dataList!!.adapter = DataAdapter(SessionManager(requireActivity()).fetchModeKeyData())
//            dataList!!.setHasFixedSize(true)
//            Log.d("ModeFragment", "dataList is not null")
        } else {
            Log.d("ModeFragment dataList", "dataList is null")
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.floatingActionButtonModeKeyAdd?.setOnClickListener {
            activity?.finish()
            startActivity(Intent(requireActivity(), ModeActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onResume() {
        super.onResume()
//        val layoutManager =
//            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        dataList = binding.recyclerViewModeKeySet
        IotApi.getModeKeyInfo(requireActivity(), SessionManager(requireActivity()))

        if (dataList != null) {
//            dataList!!.layoutManager = layoutManager
            dataList!!.adapter = DataAdapter(SessionManager(requireActivity()).fetchModeKeyData())
            dataList!!.setHasFixedSize(true)
            Log.d("ModeFragment", "dataList is not null")
        } else {
            Log.d("ModeFragment dataList", "dataList is null")
        }
    }


    class DataAdapter(private val listData: java.util.ArrayList<GetModeKeyDataInfo>) :
        RecyclerView.Adapter<DataAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        TODO("Not yet implemented")
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.mode_key_recycler_view, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        TODO("Not yet implemented")
            holder.ModeKeyButton.text = listData[position].mode_key_name
        }

        override fun getItemCount(): Int {
//        TODO("Not yet implemented")
            Log.d("getItemCount", listData.size.toString())
            return listData.size
        }

        class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val ModeKeyButton: Button = v.findViewById(com.iotApp.R.id.button_mode_key_recyclerView)
        }
    }

//    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
//        val ModeKeyButton: Button = v.findViewById(com.iotApp.R.id.button_mode_key_recyclerView)
//    }
}

