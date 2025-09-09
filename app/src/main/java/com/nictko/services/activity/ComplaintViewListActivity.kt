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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.nictko.services.R
import com.nictko.services.adapter.ComplaintListAdapter
import com.nictko.services.databinding.ActivityComplaintViewListBinding
import com.nictko.services.sharedprefrence.SessionManager
import com.nictko.services.viewmodel.AddComplaintReqViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class ComplaintViewListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityComplaintViewListBinding
    private lateinit var progressDialog: Dialog
    private val viewModel by viewModels<AddComplaintReqViewModel>()
    private lateinit var adapter: ComplaintListAdapter
    private var isFirstLaunch = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_complaint_view_list)
        val mobile = SessionManager.getString(this, "mobile")
        val koid = SessionManager.getString(this, "koid")
        val getstatename = SessionManager.getString(this, "statename")
        val getbanktype = SessionManager.getString(this, "setbanktype")
        val getmobile_other = SessionManager.getString(this, "usermobile_other")


        progressDialog = Dialog(this)
        val view = layoutInflater.inflate(R.layout.progress_dialog, null)
        progressDialog.setContentView(view)
        progressDialog.setCancelable(false)

        val sdf = SimpleDateFormat("yyyy-MM-dd ")
        val currentDate = sdf.format(Date())
        System.out.println(" CDATE is  " + currentDate)
        binding.txtFromdate.setText(currentDate)
        binding.txtTodate.setText(currentDate)

        binding.txtFromdate.setOnClickListener {
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
                    // on below line we are setting
                    // date to our edit text.
//                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
//                    val dat = (year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth.toString())
//                    binding.txtFromdate.setText(dat)

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
            // at last we are calling show
            // to display our date picker dialog.
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
                    // on below line we are setting
                    // date to our edit text.
//                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)

                    val dat =
                        (year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth.toString())
                    binding.txtTodate.setText(dat)
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

        binding.recyclerviewSearchcompaint.layoutManager = LinearLayoutManager(this)
        adapter = ComplaintListAdapter(emptyList())

        binding.recyclerviewSearchcompaint.adapter = adapter

        viewModel.complaint_list_onlysbi.observe(this) {
            progressDialog.dismiss()
            if(it.responseStatus.equals("success"))
            {
                if(it.messages.equals("No data found"))
                {    processError(it.messages)

                    binding.toplayout.setVisibility(View.GONE);
                    binding.refresh.setVisibility(View.VISIBLE);
                }
                else{

                    binding.refresh.setVisibility(View.VISIBLE);
                    binding.recyclerviewSearchcompaint.setVisibility(View.VISIBLE)
                    it.data?.let { adapter.updateData(it) }
                    binding.toplayout.setVisibility(View.GONE);

                }

            }
            else{
                progressDialog.dismiss()
//                processError(it.messages)
            }
        }

        click()

    }

    private fun click() {

        binding.profileImg.setOnClickListener {
            onBackPressed()
        }
        binding.refresh.setOnClickListener {
            binding.toplayout.setVisibility(View.VISIBLE);
            binding.recyclerviewSearchcompaint.setVisibility(View.GONE);
            binding.refresh.setVisibility(View.GONE);

        }

        binding.btnSearchcompaint.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (isInternetConnected()) {

                    progressDialog.show()
                   // if(selectbanktype.equals("SBI"))
                    if(SessionManager.getString(this, "setbanktype").equals("SBI"))
                    {
                        checkcomplaintlist()

                    }
                    else{
                        checkcomplaintlist_NONSBI()

                    }

                } else {
                    Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_SHORT)
                        .show()

                }
            } else {


                progressDialog.show()

                if(SessionManager.getString(this, "setbanktype").equals("SBI"))
                {
                    checkcomplaintlist()

                }
                else{
                    checkcomplaintlist_NONSBI()

                }

            }

//            binding.toplayout.setVisibility(View.VISIBLE);
//            binding.recyclerviewSearchcompaint.setVisibility(View.GONE);

        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun isInternetConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun checkcomplaintlist() {
        val setstartdate = binding.txtFromdate.text.toString()
        val settodate = binding.txtTodate.text.toString()
        val setusermobile = SessionManager.getString(this, "mobile")

        viewModel.callcomplaintlist_onlysbi(
            getstartdate = setstartdate,
            gettodate = settodate,
            getusermobile = setusermobile.toString());
    }
    fun checkcomplaintlist_NONSBI() {
        val setstartdate = binding.txtFromdate.text.toString()
        val settodate = binding.txtTodate.text.toString()
        val setusermobile = SessionManager.getString(this, "usermobile_other")
        viewModel.callcomplaintlist_onlysbi(
            getstartdate = setstartdate,
            gettodate = settodate,
            getusermobile = setusermobile.toString());
    }

    fun processError(msg: String?) {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

        msg?.let { Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show() }
        binding.toplayout.setVisibility(View.VISIBLE);

    }

    override fun onResume() {
        super.onResume()
        if (isFirstLaunch) {
            isFirstLaunch = false; // First time, skip loading

        } else {

            if(SessionManager.getString(this, "setbanktype").equals("SBI"))
            {
                checkcomplaintlist()

            }
            else{
                checkcomplaintlist_NONSBI()
                Log.e("sammm","yty");

            }
        }


    }

}