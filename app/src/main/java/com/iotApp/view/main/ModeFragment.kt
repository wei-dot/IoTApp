package com.iotApp.view.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.iotApp.R
import com.iotApp.Constants
import com.iotApp.api.IotApi
import com.iotApp.api.WsListener
import com.iotApp.databinding.FragmentMainModeBinding
import com.iotApp.model.GetModeKeyDataInfo
import com.iotApp.repository.SessionManager
import com.iotApp.view.ModeActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import java.util.concurrent.TimeUnit


class ModeFragment : Fragment() {
    private var _binding: FragmentMainModeBinding? = null
    private var dataList: RecyclerView? = null
    private val binding get() = _binding!!
    private var mHomeFab: ExtendedFloatingActionButton? = null
    private var mAddDeviceFab: FloatingActionButton? = null
    private var mDeviceListFab: FloatingActionButton? = null
    private var mAddDeviceText: TextView? = null
    private var mDeviceListText: TextView? = null
    private var mIsAllFabVisible: Boolean? = null
    private var mInflater: LayoutInflater? = null
    private var swipe_refresh: SwipeRefreshLayout? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainModeBinding.inflate(inflater, container, false)
        mInflater = inflater
        mHomeFab = binding.homeFab
        mAddDeviceFab = binding.addModeKeyFab
        mDeviceListFab = binding.deleteModeKeyFab
        mAddDeviceText = binding.addModeKeyText
        mDeviceListText = binding.deleteModeKeyText
        swipe_refresh = binding.swipeRefresh
        swipe_refresh!!.setProgressBackgroundColorSchemeResource(android.R.color.white)
        swipe_refresh!!.setColorSchemeResources(
            android.R.color.holo_blue_light,
            android.R.color.holo_red_light,
            android.R.color.holo_orange_light
        )
        swipe_refresh!!.setSize(SwipeRefreshLayout.LARGE)
        swipe_refresh!!.setProgressViewOffset(
            false, 0, TypedValue
                .applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 24f, resources
                        .displayMetrics
                ).toInt()
        )
        swipe_refresh!!.isEnabled = true
        swipe_refresh!!.setOnRefreshListener {
            IotApi.getModeKeyInfo(requireActivity(), SessionManager(requireActivity()))
            val layoutManager = GridLayoutManager(requireActivity(), 1)
            dataList = binding.recyclerViewModeKeySet
            if (dataList != null) {
                dataList!!.layoutManager = layoutManager
                Log.d(
                    "ModeFragment onResume",
                    "${SessionManager(requireActivity()).fetchModeKeyData()}"
                )
                dataList!!.adapter = SessionManager(requireActivity()).fetchModeKeyData()
                    .let {
                        context?.let { it1 ->
                            mInflater?.let { it2 ->
                                DataAdapter(
                                    it,
                                    it1,
                                    it2,
                                    requireActivity()
                                )
                            }
                        }
                    }
                dataList!!.setHasFixedSize(true)
            }
            swipe_refresh!!.isRefreshing = false
        }
        fabInVisibility()
        IotApi.getModeKeyInfo(requireActivity(), SessionManager(requireActivity()))
//        val layoutManager =
//            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        val layoutManager = GridLayoutManager(requireActivity(), 1)

//        layoutManager.orientation = LinearLayoutManager.VERTICAL
        dataList = binding.recyclerViewModeKeySet
        if (dataList != null) {
            dataList!!.layoutManager = layoutManager
//            dataList!!.adapter = DataAdapter(SessionManager(requireActivity()).fetchModeKeyData())
//            dataList!!.setHasFixedSize(true)
//            Log.d("ModeFragment", "dataList is not null")
        } else {
            Log.d("ModeFragment dataList", "dataList is null")
        }


        mHomeFab?.setOnClickListener {
            mIsAllFabVisible = if (!mIsAllFabVisible!!) {
                mAddDeviceFab?.show()
                mAddDeviceText?.visibility = View.VISIBLE
                mAddDeviceFab?.animate()?.translationY(-resources.getDimension(R.dimen.standard_60))
                mAddDeviceText?.animate()
                    ?.translationY(-resources.getDimension(R.dimen.standard_60))

                mDeviceListFab?.show()
                mDeviceListText?.visibility = View.VISIBLE
                mDeviceListFab?.animate()
                    ?.translationY(-resources.getDimension(R.dimen.standard_120))
                mDeviceListText?.animate()
                    ?.translationY(-resources.getDimension(R.dimen.standard_120))

                mHomeFab!!.extend()
                true
            } else {
                mAddDeviceFab?.hide()
                mAddDeviceText?.visibility = View.GONE
                mAddDeviceFab?.animate()?.translationY(0f)
                mAddDeviceText?.animate()?.translationY(0f)

                mDeviceListFab?.hide()
                mDeviceListText?.visibility = View.GONE
                mDeviceListFab?.animate()?.translationY(0f)
                mAddDeviceText?.animate()?.translationY(0f)
                mHomeFab!!.shrink()
                false
            }
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.addModeKeyFab?.setOnClickListener {
            startActivity(Intent(requireActivity(), ModeActivity::class.java))
        }
        binding.deleteModeKeyFab.setOnClickListener {
            Toast.makeText(activity, "長按要刪除的組合鍵", Toast.LENGTH_SHORT).show()
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
//        Thread {
//            while (true) {
//                runOnUiThread {
//
//                }
//            }
//        }.start()
        IotApi.getModeKeyInfo(requireActivity(), SessionManager(requireActivity()))

        if (dataList != null) {
//            dataList!!.layoutManager = layoutManager
            Log.d(
                "ModeFragment onResume",
                "${SessionManager(requireActivity()).fetchModeKeyData()}"
            )
            dataList!!.adapter = SessionManager(requireActivity()).fetchModeKeyData()
                .let {
                    context?.let { it1 ->
                        mInflater?.let { it2 ->
                            DataAdapter(
                                it,
                                it1,
                                it2,
                                requireActivity()
                            )
                        }
                    }
                }
            dataList!!.setHasFixedSize(true)
            Log.d("ModeFragment", "dataList is not null")
        } else {
            Log.d("ModeFragment dataList", "dataList is null")
        }
        dataList?.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                /**
                 * 当RecyclerView的滑动状态改变时触发
                 */
                val layoutManager =
                    LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

                override fun onScrollStateChanged(
                    @NonNull recyclerView: RecyclerView,
                    newState: Int
                ) {
                    super.onScrollStateChanged(recyclerView, newState)
                    Log.d("test", "StateChanged = $newState")
                }
            }
        )
    }


    class DataAdapter(
        private val listData: java.util.ArrayList<GetModeKeyDataInfo>,
        context: Context,
        inflater: LayoutInflater,
        activity: FragmentActivity,
//        ft:FragmentTransaction,
//        modeFragment: ModeFragment
    ) :
        RecyclerView.Adapter<DataAdapter.ViewHolder>() {
        private val mContext = context
        private val mInflater = inflater
        private val mActivity = activity
        //    private val host: String = "192.168.0.15"
        private val host: String = "192.168.0.13:8000"
        private val mWbSocketUrl = "ws://" + host + Constants.Power_Strip_URL
        private lateinit var mClient: OkHttpClient
        private lateinit var request: Request
        private lateinit var mWebSocket: WebSocket
        //        private val mFt = ft
//        private val mModeFragment = modeFragment
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

//        TODO("Not yet implemented")
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.mode_key_recycler_view, parent, false)
            mClient = OkHttpClient.Builder()
                .pingInterval(10, TimeUnit.SECONDS)
                .build()
            request = Request.Builder()
                .url(mWbSocketUrl)
                .build()
            mWebSocket = mClient.newWebSocket(request, WsListener())
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        TODO("Not yet implemented")
            holder.ModeKeyButton.text = listData[position].mode_key_name
            holder.ModeKeyButton.setOnClickListener {
                Log.d("ModeFragment", "ModeKeyButton is clicked")
            }
            holder.ModeKeyButton.setOnClickListener {
                Toast.makeText(
                    mActivity,
                    listData[position].tplink_switch_mode_key,
                    Toast.LENGTH_SHORT
                ).show()
                for(i in 1..6){
                    Log.d("ModeFragment", "for in $i tplink_switch_mode_key = ${listData[position].tplink_switch_mode_key}")
                    listData[position].tplink_switch_mode_key[i-1]
                    if(listData[position].tplink_switch_mode_key[i-1] == '1') {
                        mWebSocket.send("{\"message\":\"on:$i\"}")
                    }else{
                        mWebSocket.send("{\"message\":\"off:$i\"}")
                    }
                }
            }

            holder.ModeKeyButton.setOnLongClickListener(View.OnLongClickListener {
                val popupWindow = PopupWindow(mContext)
                val view =
                    mInflater.inflate(com.iotApp.R.layout.popup_confirm_delete_mode_key, null)
                val btnDeleteModeKey =
                    view.findViewById<ImageButton>(com.iotApp.R.id.popup_delete_mode_key)
                val btnNoDeleteModeKey =
                    view.findViewById<ImageButton>(com.iotApp.R.id.popup_no_delete_mode_key)
                btnDeleteModeKey.setOnClickListener {
                    IotApi.deleteModeKey(
                        mActivity,
                        SessionManager(mActivity),
                        listData[position].mode_key_data_id
                    )
//                    mFt.detach(mModeFragment).attach(mModeFragment).commit()
                    popupWindow.dismiss()
                    holder.ModeKeyButton.visibility = View.GONE
                }
                btnNoDeleteModeKey.setOnClickListener {
//                    val intent = Intent(mContext, FamilyActivity::class.java)
//                    intent.putExtra("FamilyMemberActivity", "editMember")
//                    startActivity(intent)
                    popupWindow.dismiss()
                }
                popupWindow.setOnDismissListener {
                    backgroundAlpha(1f, mActivity)
                }
                if (popupWindow.isShowing) {
                    popupWindow.dismiss()
                } else {
                    backgroundAlpha(0.8f, mActivity)
                    popupWindow.contentView = view
                    popupWindow.width = LinearLayout.LayoutParams.WRAP_CONTENT
                    popupWindow.height = LinearLayout.LayoutParams.WRAP_CONTENT
                    popupWindow.isFocusable = true
                    popupWindow.isOutsideTouchable = true
                    popupWindow.animationStyle = com.iotApp.R.style.normalAnimationPopup
                    popupWindow.setBackgroundDrawable(mContext.let { it1 ->
                        ContextCompat.getColor(
                            it1, com.google.android.material.R.color.mtrl_btn_transparent_bg_color
                        )
                    }.let { it2 -> ColorDrawable(it2) })
                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
                }
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

        fun updateData(viewModels: ArrayList<GetModeKeyDataInfo>?) {
            listData.clear()
            if (viewModels != null) {
                listData.addAll(viewModels)
            }
            notifyDataSetChanged()
        }

        class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val ModeKeyButton: Button = v.findViewById(com.iotApp.R.id.button_mode_key_recyclerView)
        }
    }

    private fun fabInVisibility() {
        mAddDeviceFab?.visibility = View.GONE
        mDeviceListFab?.visibility = View.GONE
        mAddDeviceText?.visibility = View.GONE
        mDeviceListText?.visibility = View.GONE
        mIsAllFabVisible = false
        mHomeFab?.shrink()
    }


//    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
//        val ModeKeyButton: Button = v.findViewById(com.iotApp.R.id.button_mode_key_recyclerView)
//    }
}
