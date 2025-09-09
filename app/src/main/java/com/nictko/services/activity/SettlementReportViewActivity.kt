package com.nictko.services.activity

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.nictko.services.R
import com.nictko.services.adapter.SettlementReportAdapter
import com.nictko.services.databinding.ActivitySettlementReportViewBinding
import com.nictko.services.sharedprefrence.SessionManager
import com.nictko.services.viewmodel.SettlementReportViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class SettlementReportViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettlementReportViewBinding
    private val viewModel by viewModels<SettlementReportViewModel>()
    private lateinit var adapter: SettlementReportAdapter
    private lateinit var progressDialog: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settlement_report_view)
        val getmobile = SessionManager.getString(this, "mobile")
        val getkoid = SessionManager.getString(this, "koid")
        val getusername = SessionManager.getString(this, "username")
        val getuserrole = SessionManager.getString(this, "userrole")
        val getsdplocation = SessionManager.getString(this, "sdplocation")
        val getuserid = SessionManager.getString(this, "userid")

        val sdf = SimpleDateFormat("yyyy-MM-dd ")
        val currentDate = sdf.format(Date())
        System.out.println(" C DATE is  "+currentDate)
        binding.txtFromdate.setText(currentDate)
        binding.txtTodate.setText(currentDate)

        progressDialog = Dialog(this)
        val view = layoutInflater.inflate(R.layout.progress_dialog, null)
        progressDialog.setContentView(view)
        progressDialog.setCancelable(false)
        binding.profileImg.setOnClickListener {
            onBackPressed()
        }

        binding.txtFromdate.setOnClickListener {
            val c = Calendar.getInstance()
            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    var date = "1"

                    //
                    val finalmonth = monthOfYear + 1
                    val finalmonthsum: String
                    if (finalmonth < 10) {
                        finalmonthsum = "0$finalmonth"
                        Log.e("final month e", "" + finalmonthsum + "")
                    } else {
                        finalmonthsum = finalmonth.toString()
                        Log.e("final month ELESE", "" + finalmonthsum + "")
                    }

                    if (dayOfMonth < 10) {
                        date = "0$dayOfMonth"
                        Log.e("if monthOfYear", "" + finalmonth)
                        //                                    binding.tvBusddate.setText(date + "/" + (monthOfYear + 1) + "/" + year);
                        binding.txtFromdate.setText("$year-$finalmonthsum-$date")
                    } else {
//                                    binding.tvBusddate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                                    binding.tvBusddate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        Log.e("if monthOfYear else ", "" + finalmonth)

                        binding.txtFromdate.setText("$year-$finalmonthsum-$dayOfMonth")
                    }

                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )

            datePickerDialog.show()
        }
        binding.txtTodate.setOnClickListener {

            // on below line we are getting
            // the instance of our calendar.
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                this,
                { view, year, monthOfYear, dayOfMonth ->
//                    val dat = (year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth.toString())
//
//                    binding.txtTodate.setText(dat)

                    var date = "1"

                    //
                    val finalmonth = monthOfYear + 1
                    val finalmonthsum: String
                    if (finalmonth < 10) {
                        finalmonthsum = "0$finalmonth"
                        Log.e("final month e", "" + finalmonthsum + "")
                    } else {
                        finalmonthsum = finalmonth.toString()
                        Log.e("final month ELESE", "" + finalmonthsum + "")
                    }

                    if (dayOfMonth < 10) {
                        date = "0$dayOfMonth"
                        Log.e("if monthOfYear", "" + finalmonth)
                        //                                    binding.tvBusddate.setText(date + "/" + (monthOfYear + 1) + "/" + year);
                        binding.txtTodate.setText("$year-$finalmonthsum-$date")
                    } else {
//                                    binding.tvBusddate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                                    binding.tvBusddate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        Log.e("if monthOfYear else ", "" + finalmonth)

                        binding.txtTodate.setText("$year-$finalmonthsum-$dayOfMonth")

                    }




                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            // at last we are calling show
            // to display our date picker dialog.
            datePickerDialog.show()
        }
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        // Initialize Adapter
        adapter = SettlementReportAdapter(emptyList())
        binding.recyclerview.adapter = adapter

        viewModel.setlemetreportResult.observe(this) {
            progressDialog.dismiss()

            if(it.responseStatus.equals("success"))
            {
                if(it.messages.equals("No data found"))
                {
                    it.messages?.let { it1 -> Snackbar.make(binding.root, it1, Snackbar.LENGTH_SHORT).show() }

                }

                else
                {
                    binding.refresh.setVisibility(View.VISIBLE);
                    binding.recyclerview.setVisibility(View.VISIBLE);
                    it.data?.let { adapter.updateData(it) } // Update the adapter
                    binding.toplayout.setVisibility(View.GONE);
                }


            }
            else{
                progressDialog.dismiss()
                processError(it.messages)
            }

        }

        binding.btnSubsetreport.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (isInternetConnected()) {
                    if(validateInputs())
                    {
                        progressDialog.show()
                        sendreqaddsettlelmnt()

                    }
                }
                else{
                    Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_SHORT).show()

                }
            }
            else{

                if(validateInputs())
                {
                    progressDialog.show()
                    sendreqaddsettlelmnt()

                }

            }

        }
        binding.refresh.setOnClickListener {
//            sendreqaddsettlelmnt()
            binding.toplayout.setVisibility(View.VISIBLE);
            binding.recyclerview.setVisibility(View.GONE);
            binding.refresh.setVisibility(View.GONE);

        }
    }

    fun sendreqaddsettlelmnt() {
        val setstartdate = binding.txtFromdate.text.toString()
        val settodate = binding.txtTodate.text.toString()
        val setkoid = SessionManager.getString(this, "koid")
        val setpage = 1
        val setsize = 10
        viewModel.callsettlementreport(getstartdate=setstartdate, gettodate=settodate,getkoid = setkoid!!,getpage= setpage ,getsize= setsize)
    }
    fun settlementconfirm(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    }
    fun processError(msg: String?) {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        msg?.let { Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show() }


    }
    override fun onBackPressed() {
        super.onBackPressed()
       finish()
    }

    private fun validateInputs(): Boolean {
        var isValid = true
        if (binding.txtFromdate.text.isNullOrEmpty()) {
            Snackbar.make(binding.root, "Choose From Date", Snackbar.LENGTH_SHORT).show()

            isValid = false
        }
        else  if (binding.txtTodate.text.isNullOrEmpty()) {
            Snackbar.make(binding.root, "Choose To Date", Snackbar.LENGTH_SHORT).show()
            isValid = false
        }
        return isValid
    }
    private fun isInternetConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}