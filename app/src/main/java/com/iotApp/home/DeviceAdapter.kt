package com.iotApp.home


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iotApp.R


class DeviceAdapter internal constructor(private var mData: ArrayList<String>) :
    RecyclerView.Adapter<DeviceAdapter.ViewHolder>() {
    var selectedPosition = -1; //make it global

    // 建立ViewHolder
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 宣告元件
        val txtItem: TextView

        init {
            txtItem = itemView.findViewById<View>(R.id.list_item) as TextView

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 連結項目布局檔list_item
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.device_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 設置txtItem要顯示的內容
        holder.txtItem.text = mData[position]
        if (selectedPosition == position)
            holder.itemView.setBackgroundColor(Color.argb(50, 170, 170, 170))
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
        holder.itemView.setOnClickListener {
            val previousItem = selectedPosition
            selectedPosition = position
            notifyItemChanged(previousItem)
            notifyItemChanged(selectedPosition)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun changeData(data: List<String>) {
        mData.clear()
        mData.addAll(data)
    }


}