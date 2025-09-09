package com.nictko.services.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nictko.services.R
import com.nictko.services.response.sbiledgerresp.Data

class SBIBankingLedgerAdapter(private var items: List<Data>) :
    RecyclerView.Adapter<SBIBankingLedgerAdapter
    .ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        val settlementId: TextView = itemView.findViewById(R.id.tv_settlement_id)
//        val settlementDate: TextView = itemView.findViewById(R.id.tv_settlement_date)
        val tv_sbicirclanmae: TextView = itemView.findViewById(R.id.tv_sbicspcirname)
        val tv_sbicspcode: TextView = itemView.findViewById(R.id.tv_sbicspcode)
        val tv_sbicspname: TextView = itemView.findViewById(R.id.tv_cspcsname)

        val tv_sbitrancount: TextView = itemView.findViewById(R.id.tv_trancount)
        val tv_sbitrantype: TextView = itemView.findViewById(R.id.tv_trantype)

        val tv_sbicomamount: TextView = itemView.findViewById(R.id.tv_cspcomamount)
        val tv_sbicsppay: TextView = itemView.findViewById(R.id.tv_sbicsppay)

        val laycspcircla: LinearLayout = itemView.findViewById(R.id.lay_cspcircallay)
        val laycspname: LinearLayout = itemView.findViewById(R.id.lay_cspname)
        val laycsppay: LinearLayout = itemView.findViewById(R.id.lay_csppay)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sbibankingledger_adapterlayout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]

//        if( position==0)
//        {
//            holder.tv_sbicirclanmae.text = item.circleName ?: "N/A"
//
//            holder.tv_sbicspcode.text=item.cspCode?: "N/A"
//            holder.tv_sbicspname.text=item.cspName?: "N/A"
//            holder.tv_sbitrancount.text=item.txnCnt?: "0"
//
//        }
//        else{
//            holder.tv_sbitrantype.text=item.transType?: "N/A"
//            holder.tv_sbicomamount.text=item.commAmt?: "0.00"
//            holder.tv_sbicsppay.text=item.cspPay?: "0.00"
//        }

        if (position == 0) {
            // Show these fields only for first item

            holder.laycspcircla.visibility = View.VISIBLE
            holder.laycspname.visibility = View.VISIBLE
            holder.laycsppay.visibility = View.VISIBLE

            holder.tv_sbicirclanmae.visibility = View.VISIBLE
            holder.tv_sbicspcode.visibility = View.VISIBLE
            holder.tv_sbicspname.visibility = View.VISIBLE
            holder.tv_sbicsppay.visibility = View.VISIBLE

            holder.tv_sbicirclanmae.text = item.circleName
            holder.tv_sbicspcode.text = item.cspCode
            holder.tv_sbicspname.text = item.cspName
            holder.tv_sbicsppay.text = item.cspPay

        } else {
            // Hide these fields for all other positions
            holder.laycspcircla.visibility = View.GONE
            holder.laycspname.visibility = View.GONE
            holder.laycsppay.visibility = View.GONE

            holder.tv_sbicirclanmae.visibility = View.GONE
            holder.tv_sbicspcode.visibility = View.GONE
            holder.tv_sbicspname.visibility = View.GONE
            holder.tv_sbicsppay.visibility = View.GONE
        }


        holder.tv_sbitrancount.text = item.txnCnt
        holder.tv_sbitrantype.text = item.transType
        holder.tv_sbicomamount.text = item.commAmt


    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: ArrayList<Data>) {
        items = newItems
        notifyDataSetChanged()
    }
}