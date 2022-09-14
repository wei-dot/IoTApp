package com.iotApp.adapter

import android.app.AlertDialog
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.iotApp.R
import com.iotApp.model.Device

class DeviceListAdapter internal constructor(private var mData: ArrayList<Device>) :
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
            AlertDialog.Builder(it.context)
                .setTitle("Device Name")
                .setMessage(mData[position].device_name)
                .setPositiveButton("OK") { dialog, which ->
                    Toast.makeText(it.context, "OK", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    Toast.makeText(it.context, "Cancel", Toast.LENGTH_SHORT).show()
                }
                .show()
//            Toast.makeText(holder.itemView.context, "你選擇了${mData[position]}", Toast.LENGTH_SHORT)
//                .show()
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun dataChange(data: ArrayList<Device>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }
}