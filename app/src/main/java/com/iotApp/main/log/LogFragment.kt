package com.iotApp.main.log

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iotApp.HomeActivity
import com.iotApp.R
import com.iotApp.databinding.FragmentMainLogBinding

class LogFragment : Fragment() {
    private var _binding: FragmentMainLogBinding? = null
    private var RecyclerViewLog: RecyclerView? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val logViewModel =
            ViewModelProvider(this)[LogViewModel::class.java]

        _binding = FragmentMainLogBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textLog
        logViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        _binding?.groupNotLogin?.visibility = View.INVISIBLE
        _binding?.groupLogin?.visibility = View.VISIBLE
        val layoutManager = GridLayoutManager(requireActivity(), 1)
        var testList = ArrayList<String>()
        testList.add("[2022/08/02] 23:22:01 " +
                "攝影機 開關1 設定：開")
        testList.add("[2022/08/9] 08:19:56 " +
                "一氧化碳感測器 開關1 設定：開")
        testList.add("[2022/08/15] 20:45:01 " +
                "紅外線(人體感測器) 開關1 設定：開")
        RecyclerViewLog = binding.recyclerViewLog
        RecyclerViewLog!!.layoutManager = layoutManager

        RecyclerViewLog!!.adapter = DataAdapter(testList)
        RecyclerViewLog!!.setHasFixedSize(true)
        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.btnLogin?.setOnClickListener {
            val intent = Intent(requireActivity(), HomeActivity::class.java)
            intent.putExtra("Home", "AddDevice")
            startActivity(intent)
        }
    }
    //----------------------------------------------------
    class DataAdapter(
        private val listData: ArrayList<String>,
//        context: Context,
//        inflater: LayoutInflater,
//        activity: FragmentActivity,
    ) :
        RecyclerView.Adapter<DataAdapter.ViewHolder>() {
//        private val mContext = context
//        private val mInflater = inflater
//        private val mActivity = activity

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.text_log_recycler_view, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.LogText.setText(listData[position])
            holder.LogText.setOnClickListener {
                Log.d("ModeFragment", "ModeKeyButton is clicked")
            }

            holder.LogText.setOnLongClickListener(View.OnLongClickListener {
                true
            })

        }

        override fun getItemCount(): Int {
//        TODO("Not yet implemented")
            Log.d("getItemCount", listData.size.toString())
            return listData.size
        }

        private fun backgroundAlpha(f: Float, activity: Activity) {
            val lp = activity.window?.attributes
            lp?.alpha = f
            activity.window?.attributes = lp
        }


        class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val LogText: TextView = v.findViewById(com.iotApp.R.id.text_log_recycler_view)
        }
    }

}