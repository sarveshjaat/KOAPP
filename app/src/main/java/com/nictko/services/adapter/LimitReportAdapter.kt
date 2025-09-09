package com.nictko.services.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nictko.services.R
import com.nictko.services.response.limitreport.Data

class LimitReportAdapter (private var items: List<Data>) :
    RecyclerView.Adapter<LimitReportAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        val settlementId: TextView = itemView.findViewById(R.id.tv_settlement_id)
//        val settlementDate: TextView = itemView.findViewById(R.id.tv_settlement_date)
        val tvkoid: TextView = itemView.findViewById(R.id.tv_koid)
        val tvname: TextView = itemView.findViewById(R.id.tv_name)
        val tvlocation: TextView = itemView.findViewById(R.id.tv_location)
        val tvmobile: TextView = itemView.findViewById(R.id.tv_mobile)
        val tvdepdate: TextView = itemView.findViewById(R.id.tv_lddate)
        val tvpaymode: TextView = itemView.findViewById(R.id.tv_lpmode)
        val tvlamount: TextView = itemView.findViewById(R.id.tv_lamount)

        val tvlsttaus: TextView = itemView.findViewById(R.id.tv_llsttaus)
        val tvcheckdate: TextView = itemView.findViewById(R.id.tv_checkdate)
        val tvcheckno: TextView = itemView.findViewById(R.id.tv_checno)
        val tvcheckbank: TextView = itemView.findViewById(R.id.tv_checkbank)
        val tvbrname: TextView = itemView.findViewById(R.id.tv_brname)
        val tvbcode: TextView = itemView.findViewById(R.id.tv_brcode)
        val tvtrno: TextView = itemView.findViewById(R.id.tv_trno)
        val tventrydate: TextView = itemView.findViewById(R.id.tv_entrydate)
        val tvupdatedate: TextView = itemView.findViewById(R.id.tv_updatedate)

        //cheque
        val layfifthcheck: LinearLayout = itemView.findViewById(R.id.toplayoutfifthcheck)
        val layfifthcheckdata: LinearLayout = itemView.findViewById(R.id.toplayoutfifthdatacheck)
        val laysixcheque: LinearLayout = itemView.findViewById(R.id.toplayoutsixcheck)
        val laysixchecquedata: LinearLayout = itemView.findViewById(R.id.toplayoutsixdatacheck)
        //cash
        val laysevenbycash: LinearLayout = itemView.findViewById(R.id.toplayoutsevencash)
        val laysevenbycashdata: LinearLayout = itemView.findViewById(R.id.toplayoutsevendatacash)
        //net
        val layeightbynet: LinearLayout = itemView.findViewById(R.id.toplayouteightnetbanking)
        val layeightbynetdata: LinearLayout = itemView.findViewById(R.id.toplayouteightdatanetbanking)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.limitreportadapter_lay, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
//        holder.settlementId.text = item.id.toString()
//        holder.settlementDate.text = item.date
        Log.e("whats is here ","ko id"+ item.koid.toString())

        if(item.payMode.toString().equals("Cash"))
        {
            holder.tvname.text = item.name.toString()
            holder.tvkoid.text = item.koid.toString()
            holder.tvlocation.text = item.location.toString()
            holder.tvmobile.text = item.mobno.toString()
            holder.tvdepdate.text = item.depDate.toString()
            holder.tvpaymode.text = item.payMode.toString()
            holder.tvlsttaus.text = item.kstatus.toString()
            holder.tvlamount.text = item.amt.toString()
            holder.tvbrname.text = item.brname.toString()
            holder.tvbcode.text = item.brcode.toString()
            holder.tventrydate.text = item.entryDatetime.toString()
            holder.tvupdatedate.text = item.statusDatetime.toString()

            //cheque
            holder.layfifthcheck.visibility=View.GONE
            holder.layfifthcheckdata.visibility=View.GONE
            holder.laysixcheque.visibility=View.GONE
            holder.laysixchecquedata.visibility=View.GONE

            //net
            holder.layeightbynet.visibility=View.GONE
            holder.layeightbynetdata.visibility=View.GONE



        }
        else if(item.payMode.toString().equals("Cheque"))
        {
            holder.tvname.text = item.name.toString()
            holder.tvkoid.text = item.koid.toString()
            holder.tvlocation.text = item.location.toString()
            holder.tvmobile.text = item.mobno.toString()
            holder.tvdepdate.text = item.depDate.toString()
            holder.tvpaymode.text = item.payMode.toString()
            holder.tvlsttaus.text = item.kstatus.toString()
            holder.tvlamount.text = item.amt.toString()
            holder.tvcheckdate.text = item.chqDate.toString()
            holder.tvcheckno.text = item.chqno.toString()
            holder.tvcheckbank.text = item.chqbank.toString()
            holder.tventrydate.text = item.entryDatetime.toString()
            holder.tvupdatedate.text = item.statusDatetime.toString()
//cash
            holder.laysevenbycash.visibility=View.GONE
            holder.laysevenbycashdata.visibility=View.GONE
 //net
            holder.layeightbynet.visibility=View.GONE
            holder.layeightbynetdata.visibility=View.GONE

        }
        else{
            holder.tvname.text = item.name.toString()
            holder.tvkoid.text = item.koid.toString()
            holder.tvlocation.text = item.location.toString()
            holder.tvmobile.text = item.mobno.toString()
            holder.tvdepdate.text = item.depDate.toString()
            holder.tvpaymode.text = item.payMode.toString()
            holder.tvlsttaus.text = item.kstatus.toString()
            holder.tvlamount.text = item.amt.toString()
            holder.tvtrno.text = item.txnno.toString()

            //cash
            holder.laysevenbycash.visibility=View.GONE
            holder.laysevenbycashdata.visibility=View.GONE
            //cheque
            holder.layfifthcheck.visibility=View.GONE
            holder.layfifthcheckdata.visibility=View.GONE
            holder.laysixcheque.visibility=View.GONE
            holder.laysixchecquedata.visibility=View.GONE
            holder.tventrydate.text = item.entryDatetime.toString()
            holder.tvupdatedate.text = item.statusDatetime.toString()

        }



    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: ArrayList<Data>) {
        items = newItems
        notifyDataSetChanged()
    }
}