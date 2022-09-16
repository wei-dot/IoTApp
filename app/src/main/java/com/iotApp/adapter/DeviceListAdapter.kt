package com.iotApp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.iotApp.R
import com.iotApp.model.Device
import com.iotApp.repository.SessionManager
import com.iotApp.viewmodel.DeviceViewModel


class DeviceListAdapter internal constructor(
    private var mData: ArrayList<Device>,
    private val viewModel: DeviceViewModel
) :
    RecyclerView.Adapter<DeviceListAdapter.ViewHolder>() {
    var selectedPosition = -1 //make it global

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtItem: TextView

        init {
            txtItem = itemView.findViewById<View>(R.id.device_name) as TextView

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_device_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtItem.text = mData[position].device_name
        if (selectedPosition == position)
            holder.itemView.setBackgroundColor(Color.argb(50, 170, 170, 170))
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
        holder.itemView.setOnClickListener {
            val previousItem = selectedPosition
            selectedPosition = position
            notifyItemChanged(previousItem)
            notifyItemChanged(selectedPosition)
            MaterialAlertDialogBuilder(it.context, R.style.MaterialAlertDialog_Rounded)
                .setTitle("設備資訊")
                .setMessage("設備名稱: ${mData[position].device_name}\n設備編號: ${mData[position].id}\n設備類型: ${mData[position].device_type}\n新增時間: ${mData[position].time}")
                .setBackground(
                    ContextCompat.getDrawable(
                        holder.itemView.context,
                        R.drawable.background_popup
                    )
                )
                .setPositiveButton("確認") { _, _ ->
                }
                .setNegativeButton("刪除設備") { _, _ ->
                    SessionManager(holder.itemView.context).fetchAuthToken()?.let { it1 ->
                        viewModel.deleteDevice(
                            holder.itemView.context,
                            "Token $it1", mData[position].id
                        )
                        viewModel.getDevice("Token $it1")
                    }

                }.create().show()
        }
        holder.itemView.setOnLongClickListener { it ->
            val alertDialog =
                MaterialAlertDialogBuilder(it.context, R.style.MaterialAlertDialog_Rounded)
                    .setTitle("修改設備名稱")
                    .setBackground(
                        ContextCompat.getDrawable(
                            holder.itemView.context,
                            R.drawable.background_popup
                        )
                    )
                    .setMessage("是否要修改設備名稱??").create()
            // Set up the input
            val viewDialog = View.inflate(it.context, R.layout.dialog_change_name, null)
            alertDialog.setView(viewDialog)
            val cancel = viewDialog.findViewById<MaterialButton>(R.id.cancel)
            val confirm = viewDialog.findViewById<MaterialButton>(R.id.ok)
            val deviceName = viewDialog.findViewById<TextInputEditText>(R.id.et_device_name)
            cancel.setOnClickListener {
                alertDialog.dismiss()
            }
            confirm.setOnClickListener {
                val temp = mData[position]
                temp.device_name = deviceName.text.toString()
                if (deviceName.text.toString().isNotEmpty()) {
                    SessionManager(holder.itemView.context).fetchAuthToken()?.let { it1 ->
                        viewModel.changeDeviceName(
                            it.context,
                            id = mData[position].id,
                            token = "Token $it1",
                            name = temp,
                        )
                        viewModel.getDevice("Token $it1")
                    }
                } else {
                    deviceName.error = "請輸入設備名稱"
                }
                alertDialog.dismiss()
            }
            alertDialog.show()
            true
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }


}