package com.nictko.services.activity

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.nictko.services.R
import com.nictko.services.adapter.SBIBankingLedgerAdapter
import com.nictko.services.databinding.ActivityNationalBankingLedgerBinding
import com.nictko.services.sharedprefrence.SessionManager
import com.nictko.services.viewmodel.BankingLedgerViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class NationalBankingLedgerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNationalBankingLedgerBinding;
    private lateinit var progressDialog: Dialog
    var monthtype: String? = null
    var monthtype1: String? = null
    var yeartype: String? = null
    private val viewModelbanking by viewModels<BankingLedgerViewModel>()
    private lateinit var adapter: SBIBankingLedgerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_national_banking_ledger)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_national_banking_ledger)

        val mobile = SessionManager.getString(this, "mobile")
        val koid = SessionManager.getString(this, "koid")
        val getstatename = SessionManager.getString(this, "statename")
        val getdivisionname = SessionManager.getString(this, "divisionname")
        val getdistrictname = SessionManager.getString(this, "dname")
        val getuname = SessionManager.getString(this, "username")
        val getbanktype = SessionManager.getString(this, "setbanktype")
        progressDialog = Dialog(this)
        val view = layoutInflater.inflate(R.layout.progress_dialog, null)
        progressDialog.setContentView(view)
        progressDialog.setCancelable(false)

        val monthFormat = SimpleDateFormat("MMMM", Locale.ENGLISH)
        val monthFormatnumber = SimpleDateFormat("MM", Locale.ENGLISH) // "MM" gives 01, 02, ..., 12

        val yearFormat = SimpleDateFormat("yyyy", Locale.ENGLISH)
        val currentDate = Date()
        val currentMonth = monthFormat.format(currentDate) // e.g., "July"
        val currentMonth1 = monthFormatnumber.format(currentDate) // e.g., "July"
        val currentYear = yearFormat.format(currentDate)

        monthtype = currentMonth
        monthtype1 = currentMonth1
        yeartype = currentYear
        binding.txtSelecttv.setText("$currentMonth $currentYear")
        binding.tvSetmonthyear.setText("$monthtype $yeartype")

        binding.recyclerviewSbiledger.layoutManager = LinearLayoutManager(this)
        adapter = SBIBankingLedgerAdapter(emptyList())

        binding.recyclerviewSbiledger.adapter = adapter

        viewModelbanking.sbiResult.observe(this) {
            progressDialog.show()

            if(it.messages.equals("Invalid credential."))
            {
                progressDialog.dismiss()
                processError(it.messages)
                binding.toplayout.setVisibility(View.VISIBLE);
                binding.refresh.visibility = View.GONE
                binding.recyclerviewSbiledger.visibility = View.GONE

            }
            else{

//                binding.refresh.visibility = View.VISIBLE
//                binding.toplayout.setVisibility(View.GONE);
//                binding.secondLay.setVisibility(View.VISIBLE);
//                binding.recyclerviewSbiledger.visibility = View.VISIBLE
//
//                it.data?.let { adapter.updateData(it) }
//                progressDialog.dismiss()

                val dataList = it.data

                if (!dataList.isNullOrEmpty()) {
                    val firstItem = dataList[0]

                    // ✅ Check if csp_name is null or empty at position 0
                    if (firstItem.cspName.isNullOrBlank()) {
                        binding.toplayout.visibility = View.VISIBLE
                        binding.secondLay.visibility = View.GONE
                        binding.recyclerviewSbiledger.visibility = View.GONE
                        binding.refresh.visibility = View.GONE
                        processnodata("Data Not Found")
                        progressDialog.dismiss()
                        return@observe
                    }

                    // ✅ If csp_name is valid
                    binding.toplayout.visibility = View.GONE
                    binding.secondLay.visibility = View.VISIBLE
                    binding.recyclerviewSbiledger.visibility = View.VISIBLE
                    binding.refresh.visibility = View.VISIBLE
                    adapter.updateData(dataList)
                } else {
                    // Handle empty data case
                    binding.toplayout.visibility = View.VISIBLE
                    binding.recyclerviewSbiledger.visibility = View.GONE
                    binding.refresh.visibility = View.GONE
                }

                Handler(Looper.getMainLooper()).postDelayed({
                    progressDialog.dismiss()
                }, 300)


            }

        }


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
                    val monthName = SimpleDateFormat("MM", Locale.ENGLISH).format(
                        Calendar.getInstance().apply {
                            set(Calendar.MONTH, month)
                        }.time
                    )
                    val monthName1 = SimpleDateFormat("MMMM", Locale.ENGLISH).format(
                        Calendar.getInstance().apply {
                            set(Calendar.MONTH, month)
                        }.time
                    )

                    // Format as JSON-like string or just log values
                    val result = """{ "month": "$monthName", "year": "$year" }"""
                    Log.e("SelectedMonthYear", result)
                   // monthtype = monthName
                    monthtype1 = monthName

                    yeartype = year.toString()
                    // Show in TextView (optional)
//                    binding.txtSelecttv.text = "$monthName $year"
                    binding.tvSetmonthyear.setText("$monthName1 $yeartype")

                    binding.txtSelecttv.text =
                        Editable.Factory.getInstance().newEditable("$monthName1 $year")

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
                        Snackbar.make(
                            binding.root,
                            "Please Select Month and Year",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    } else {
                        searchSBI()

                    }

                }


            } else {
                Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_SHORT).show()
            }
        } else {
            binding.btnSublimit.setOnClickListener {

                if (binding.txtSelecttv.text.toString().equals("")) {
                    Snackbar.make(
                        binding.root,
                        "Please Select Month and Year",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                } else {
                    searchSBI()
                }

            }
        }

    }

    fun processError(msg: String?) {
        msg?.let { Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show() }
    }


    private fun isInternetConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun searchSBI() {
        progressDialog.show()
        val setmonth = monthtype1
        val setyear = yeartype
        val setapikey = ""
        //  val setapikey = "41dlwk4a"
        val setbank = SessionManager.getString(this, "setbanktype")
//        val setbank = "SBI"
         val setuserid = SessionManager.getString(this, "koid")
//        val setuserid = "1A76E648"
        viewModelbanking.searchbySBI(getmonth = setmonth.toString(), getyear = setyear.toString() ,getapikey = setapikey.toString() ,getbank=setbank.toString() ,getuserid=setuserid.toString())
    }


    fun processnodata(msg: String?) {
        msg?.let { Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show() }
    }

}