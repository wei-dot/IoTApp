package com.iotApp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.iotApp.R
import com.iotApp.model.DHT11
import com.iotApp.model.DeviceData
import com.iotApp.model.MQ7

class LogAdapter internal constructor(private var mData: ArrayList<DeviceData>) :
    RecyclerView.Adapter<LogAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtItem: TextView

        init {
            txtItem = itemView.findViewById<View>(R.id.device_data) as TextView
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_log, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val value = when (mData[position].type) {
            "DHT11" -> {
                val data: DHT11 = Gson().fromJson(mData[position].status, DHT11::class.java)
                "溫度: ${data.temp} 濕度: ${data.hum}"
            }
            "MQ7" -> {
                val data: MQ7 = Gson().fromJson(mData[position].status, MQ7::class.java)
                "濃度: ${data.co}"
            }
            else -> {
                mData[position].status
            }
        }
        holder.txtItem.text =
            "${mData[position].time}\t${mData[position].name}\t$value"

    }

    override fun getItemCount(): Int {
        return mData.size
    }
}