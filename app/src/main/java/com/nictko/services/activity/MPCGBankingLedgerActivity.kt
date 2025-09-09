package com.nictko.services.activity

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.nictko.services.R
import com.nictko.services.databinding.ActivityMpcgbankingLedgerBinding
import com.nictko.services.databinding.ActivityNationalBankingLedgerBinding
import com.nictko.services.sharedprefrence.SessionManager
import com.nictko.services.viewmodel.BankingLedgerViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MPCGBankingLedgerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMpcgbankingLedgerBinding;
    private lateinit var progressDialog: Dialog
    var monthtype: String? = null
    var yeartype: String? = null
    private val viewModelbanking by viewModels<BankingLedgerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_mpcgbanking_ledger)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mpcgbanking_ledger)

        val mobile = SessionManager.getString(this, "mobile")
        val koid = SessionManager.getString(this, "koid")
        val getstatename = SessionManager.getString(this, "statename")
        val getdivisionname = SessionManager.getString(this, "divisionname")
        val getdistrictname = SessionManager.getString(this, "dname")
        val getuname = SessionManager.getString(this, "username")
        val getbanktype = SessionManager.getString(this, "setbanktype")


        val monthFormat = SimpleDateFormat("MMMM", Locale.ENGLISH)
        val yearFormat = SimpleDateFormat("yyyy", Locale.ENGLISH)
        val currentDate = Date()
        val currentMonth = monthFormat.format(currentDate) // e.g., "July"
        val currentYear = yearFormat.format(currentDate)

        monthtype=currentMonth
        yeartype=currentYear
        binding.txtSelecttv.setText("$currentMonth $currentYear")
        progressDialog = Dialog(this)
        val view = layoutInflater.inflate(R.layout.progress_dialog, null)

        progressDialog.setContentView(view)
        progressDialog.setCancelable(false)

        click()

    }

    private fun click() {
        binding.profileImg.setOnClickListener {
            onBackPressed()
        }
        binding.txtSelecttv.setOnClickListener {

            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, _ ->

                    // Get month name
                    val monthName = SimpleDateFormat("MMMM", Locale.ENGLISH).format(
                        Calendar.getInstance().apply {
                            set(Calendar.MONTH, month)
                        }.time
                    )

                    // Format as JSON-like string or just log values
                    val result = """{ "month": "$monthName", "year": "$year" }"""
                    Log.e("SelectedMonthYear", result)
                    monthtype=monthName
                    yeartype= year.toString()
                    // Show in TextView (optional)
//                    binding.txtSelecttv.text = "$monthName $year"
                    binding.txtSelecttv.text = Editable.Factory.getInstance().newEditable("$monthName $year")

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

// Hide the day spinner using reflection
            try {
                val datePicker = datePickerDialog.datePicker
                val daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android")
                val daySpinner = datePicker.findViewById<View>(daySpinnerId)
                daySpinner?.visibility = View.GONE
            } catch (e: Exception) {
                e.printStackTrace()
            }

            datePickerDialog.show()

        }

        binding.refresh.setOnClickListener {
            binding.toplayout.setVisibility(View.VISIBLE);
            binding.secondLay.setVisibility(View.GONE);
            binding.refresh.setVisibility(View.GONE);

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (isInternetConnected()) {

                binding.btnSublimit.setOnClickListener {

                    if (binding.txtSelecttv.text.toString().equals("")) {
                        Snackbar.make(binding.root, "Please Select Month and Year", Snackbar.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    else
                    {
                       // searchboi()
                    }

                }


            }
            else{
                Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_SHORT).show()
            }
        }
        else{
            binding.btnSublimit.setOnClickListener {

                if (binding.txtSelecttv.text.toString().equals("")) {
                    Snackbar.make(binding.root, "Please Select Month and Year", Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                else
                {
                    //searchboi()
                }

            }

        }

    }

    fun processError(msg: String?) {
        msg?.let { Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show() }
    }


    private fun isInternetConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}