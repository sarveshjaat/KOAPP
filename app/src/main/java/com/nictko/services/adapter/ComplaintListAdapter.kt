package com.nictko.services.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nictko.services.R
import com.nictko.services.activity.ComplaintFeedbackActivity
import com.nictko.services.response.compliantlistresponse.Data
import com.nictko.services.sharedprefrence.SessionManager

class ComplaintListAdapter(private var items: List<Data>) :
    RecyclerView.Adapter<ComplaintListAdapter
    .ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        val settlementId: TextView = itemView.findViewById(R.id.tv_settlement_id)
//        val settlementDate: TextView = itemView.findViewById(R.id.tv_settlement_date)
        val tvcsplname: TextView = itemView.findViewById(R.id.tv_cspname)
        val tvbankname: TextView = itemView.findViewById(R.id.tv_bname)
        val tvsttaename: TextView = itemView.findViewById(R.id.tv_cspcsname)
        val tvccdiname: TextView = itemView.findViewById(R.id.tv_cspcdname)
        val tvccdtname: TextView = itemView.findViewById(R.id.tv_cspcdtname)
        val tvccmobile: TextView = itemView.findViewById(R.id.tv_cspccmobile)
        val tvcccid: TextView = itemView.findViewById(R.id.tv_cspccid)
        val tvccserviceid: TextView = itemView.findViewById(R.id.tv_cspccserviceid)
        val tvcspcomplaintmsg: TextView = itemView.findViewById(R.id.tv_cspccmsg)
        val tvsubmitreview: TextView = itemView.findViewById(R.id.tv_submitreview)
        val tvcompsttaus: TextView = itemView.findViewById(R.id.tv_cspcompsttaus)
        val tvcomplain_date: TextView = itemView.findViewById(R.id.tv_compltdate)
        val tvcomplain_resolvedate: TextView = itemView.findViewById(R.id.tv_compltresovedate)
        val tvcomplain_responsemsg: TextView = itemView.findViewById(R.id.tv_csprespmsg)
        val tvcomplain_feedbackmsg: TextView = itemView.findViewById(R.id.tv_cspfeedbackmsg)

        val tvcomplain_layoutfeedback: LinearLayout = itemView.findViewById(R.id.lay_feedback)
        val tvcomplain_layoutfeedbackmsg: LinearLayout = itemView.findViewById(R.id.lay_feedbackmsg)
        val tvcomplain_layoutresponse: LinearLayout = itemView.findViewById(R.id.lay_response)
        val tvcomplain_layoutresponsemsg: LinearLayout = itemView.findViewById(R.id.lay_responsemsg)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.complaintlist_adapter_all, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
//        Log.e("whats is here ","ko id"+ item.koid.toString())
        holder.tvcsplname.text = item.name.toString()
        holder.tvbankname.text = item.bankName.toString()
        holder.tvsttaename.text = item.state.toString()
        holder.tvccdiname.text = item.division.toString()
        holder.tvccdtname.text = item.dist.toString()
        holder.tvccmobile.text = item.mobile.toString()
        holder.tvcccid.text = item.id.toString()
        holder.tvccserviceid.text = item.serviceId.toString()
        holder.tvcspcomplaintmsg.text = item.msg.toString()
        holder.tvcomplain_date.text = item.log_time.toString()
        val context = holder.itemView.context


        if(item.feedback==null)
        {
            holder.tvcomplain_layoutfeedback.visibility = View.GONE
            holder.tvcomplain_layoutfeedbackmsg.visibility = View.GONE
        }
        else{
            holder.tvcomplain_layoutfeedback.visibility = View.VISIBLE
            holder.tvcomplain_layoutfeedbackmsg.visibility = View.VISIBLE

            if(item.feedback.equals("Satisfied"))
            {
                holder.tvcomplain_feedbackmsg.setTextColor(ContextCompat.getColor(context, R.color.darkgreen))
                holder.tvcomplain_feedbackmsg.text = item.feedback.toString()

            }
            else{
                holder.tvcomplain_feedbackmsg.setTextColor(ContextCompat.getColor(context, R.color.darkorange))

                holder.tvcomplain_feedbackmsg.text = item.feedback.toString()

            }

        }



        if(item.resolution_time==null)
        {
            holder.tvcomplain_resolvedate.text = ""

        }
        else{
            holder.tvcomplain_resolvedate.text = item.resolution_time.toString()

        }

        if(item.response_text==null)
        {
//            holder.tvcomplain_responsemsg.text = ""
            holder.tvcomplain_layoutresponse.visibility = View.GONE
            holder.tvcomplain_layoutresponsemsg.visibility = View.GONE
        }
        else{
            holder.tvcomplain_layoutresponse.visibility = View.VISIBLE
            holder.tvcomplain_layoutresponsemsg.visibility = View.VISIBLE
            holder.tvcomplain_responsemsg.text = item.response_text.toString().trim()

        }

        if (item.call_status.equals("Resolved")) {
            holder.tvcompsttaus.text = item.call_status.toString()
            holder.tvcompsttaus.setTextColor(ContextCompat.getColor(context, R.color.darkgreen))
        } else {
            holder.tvcompsttaus.text = item.call_status.toString()
            holder.tvcompsttaus.setTextColor(ContextCompat.getColor(context, R.color.darkorange))

        }

        if (item.call_status.equals("Resolved")) {
//            holder.tvsubmitreview.visibility = View.GONE

            if (item.feedback == null) {

                holder.tvsubmitreview.visibility = View.VISIBLE

                holder.tvsubmitreview.setOnClickListener {
                    val context = holder.itemView.context
                    SessionManager.saveString(context, "complaintid", item.id.toString())
                    val intent = Intent(context, ComplaintFeedbackActivity::class.java)
                    context.startActivity(intent)
                }
            } else {
                holder.tvsubmitreview.visibility = View.GONE

            }

        } else {

            holder.tvsubmitreview.visibility = View.GONE

        }


    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: ArrayList<Data>) {
        items = newItems
        notifyDataSetChanged()
    }
}