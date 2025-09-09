package com.nictko.services.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nictko.services.R
import com.nictko.services.response.settlementreport.Data
import org.json.JSONObject
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale


class SettlementReportAdapter(private var items: List<Data>) :
    RecyclerView.Adapter<SettlementReportAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val settlementId: TextView = itemView.findViewById(R.id.tv_settlement_id)
//        val settlementDate: TextView = itemView.findViewById(R.id.tv_settlement_date)
        val tvkoid: TextView = itemView.findViewById(R.id.tv_koid)
        val tvrefno: TextView = itemView.findViewById(R.id.tv_refno)
        val tvmobile: TextView = itemView.findViewById(R.id.tv_mobile)
        val tvamount: TextView = itemView.findViewById(R.id.tv_amount)
        val tvsttype: TextView = itemView.findViewById(R.id.tv_sttype)
        val tvstamount: TextView = itemView.findViewById(R.id.tv_stamont)
        val tvststtaus: TextView = itemView.findViewById(R.id.tv_ststtaus)
        val tvdatetime: TextView = itemView.findViewById(R.id.tv_datetime)
        val tventrydate: TextView = itemView.findViewById(R.id.tv_entrydate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.settlmenreporttadapter_lay, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        Log.e("whats is here ","ko id"+ item.koid.toString())
        holder.tvkoid.text = item.koid.toString()
        holder.tvrefno.text = item.ref.toString()
        holder.tvmobile.text = item.mobileno.toString()
        holder.tvamount.text = item.amount.toString()
        holder.tvsttype.text = item.stType.toString()
        holder.tvstamount.text = item.setAmount.toString()
        holder.tvststtaus.text = item.statusAcc.toString()

        if(item.statusAccTm != null)
        {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm a", Locale.getDefault())

            val updatedate = "{\"datetimeupdate\": ${item.statusAccTm}}"
            val jsonObjectupdate = JSONObject(updatedate)
            val timestampupdate = jsonObjectupdate.getLong("datetimeupdate") // Get the timestamp from JSON
            val dateupdate = Date(timestampupdate)
            val formattedDateupdate = dateFormat.format(dateupdate)
            holder.tvdatetime.text = formattedDateupdate
        }
        else{


        }
        if(item.edt!= null)
        {
            val enterydate = "{\"datetimeentry\": ${item.edt}}"
            val jsonObjectentry = JSONObject(enterydate)
            val timestampentry = jsonObjectentry.getLong("datetimeentry") // Get the timestamp from JSON

            val dateentry = Date(timestampentry)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm a", Locale.getDefault())
            val formattedDateentry = dateFormat.format(dateentry)


            holder.tventrydate.text = formattedDateentry
        }
        else{

        }

//        val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm ", Locale.getDefault())
////        sdf.setTimeZone(TimeZone.getTimeZone("UTC")) // Ensure UTC time
//        sdf.setTimeZone(TimeZone.getDefault() ) // Ensure UTC time
//        Log.e("whatsDATE ","TIME"+ item.statusAccTm)
//
//        val updatedate = sdf.format(Date(item.statusAccTm!!))
//        val entrydate = sdf.format(Date(item.edt!!))
////        val updatedate = sdf.format(Date(item.statusAccTm!! * 1000))  // If timestamp is in seconds
////        val entrydate = sdf.format(Date(item.edt!! * 1000))
//        holder.tvdatetime.text = updatedate
//        holder.tventrydate.text = entrydate






    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: ArrayList<Data>) {
        items = newItems
        notifyDataSetChanged()
    }
}