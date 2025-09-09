package com.nictko.services.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nictko.services.response.shownotice.Data
import com.nictko.services.R

class ShowNoticeAdapter (private var items: List<Data>) :
    RecyclerView.Adapter<ShowNoticeAdapter
        .ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        val settlementId: TextView = itemView.findViewById(R.id.tv_settlement_id)
//        val settlementDate: TextView = itemView.findViewById(R.id.tv_settlement_date)
        val tvnoticedtl: TextView = itemView.findViewById(R.id.tv_noticename)
        val tvnotidate: TextView = itemView.findViewById(R.id.tv_noticedate)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.noticeshow_adapter_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
//        Log.e("whats is here ","ko id"+ item.koid.toString())
        holder.tvnoticedtl.text = item.noticeDesc.toString()
        holder.tvnotidate.text = item.crntDate.toString()

    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: ArrayList<Data>) {
        items = newItems
        notifyDataSetChanged()
    }
}