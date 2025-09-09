package com.nictko.services.activity.activityother

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.nictko.services.R
import com.nictko.services.databinding.ActivityMgbbankingLedgerBinding
import com.nictko.services.databinding.ActivityMpgbbankingBinding
import com.nictko.services.sharedprefrence.SessionManager
import com.nictko.services.viewmodel.BankingLedgerViewModel
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MPGBBankingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMpgbbankingBinding;
    private val viewModelbanking by viewModels<BankingLedgerViewModel>()
    private lateinit var progressDialog: Dialog

    var monthtype: String? = null
    var yeartype: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_mpgbbanking)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mpgbbanking)


        val monthFormat = SimpleDateFormat("MMMM", Locale.ENGLISH)
        val yearFormat = SimpleDateFormat("yyyy", Locale.ENGLISH)
        val currentDate = Date()
        val currentMonth = monthFormat.format(currentDate) // e.g., "July"
        val currentYear = yearFormat.format(currentDate)
        monthtype=currentMonth
        yeartype=currentYear
        binding.txtSelecttv.setText("$currentMonth $currentYear")
        binding.tvSetmonthyear.setText("$monthtype $yeartype")

        progressDialog = Dialog(this)
        val view = layoutInflater.inflate(R.layout.progress_dialog, null)
        progressDialog.setContentView(view)
        progressDialog.setCancelable(false)

        viewModelbanking.mpgbResult.observe(this) {
            if(it.messages.equals("Invalid credential."))
            {
                progressDialog.dismiss()
                processError(it.messages)
                binding.toplayout.setVisibility(View.VISIBLE);
                binding.refresh.visibility = View.GONE

            }
            else{

                binding.refresh.visibility = View.VISIBLE
                binding.toplayout.setVisibility(View.GONE);
                binding.secondLay.setVisibility(View.VISIBLE);

                val ledgerList = it.data
                if (!ledgerList.isNullOrEmpty()) {
                    val item = ledgerList[0]

                    val container = findViewById<LinearLayout>(R.id.dynamic_text_container_mpgb)
                    container.removeAllViews()

                    // Convert the item to JSON
                    val gson = Gson()
                    val json = gson.toJson(item)
                    val jsonObject = JSONObject(json)
                    val keys = jsonObject.keys()

                    // Iterate over each key-value pair
                    while (keys.hasNext()) {
                        val key = keys.next()
                        val value = jsonObject.getString(key)

                        // Create horizontal LinearLayout for each key-value row
                        val rowLayout = LinearLayout(this)
                        rowLayout.layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        rowLayout.orientation = LinearLayout.HORIZONTAL
                        rowLayout.setPadding(8, 4, 8, 4)

                        // Key TextView
                        val keyTextView = TextView(this)
                        keyTextView.text = "$key:"
                        keyTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
                        keyTextView.setTypeface(null, Typeface.BOLD)
                        keyTextView.setTextColor(
                            ContextCompat.getColor(
                                this,
                                android.R.color.holo_blue_dark
                            )
                        )
                        keyTextView.layoutParams = LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1f
                        )

                        // Value TextView
                        val valueTextView = TextView(this)
                        valueTextView.text = value
                        valueTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
                        valueTextView.setTextColor(
                            ContextCompat.getColor(
                                this,
                                android.R.color.black
                            )
                        )
                        valueTextView.layoutParams = LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1f
                        )

                        // Add key and value views to the row
                        rowLayout.addView(keyTextView)
                        rowLayout.addView(valueTextView)

                        // Add the row to the main container
                        container.addView(rowLayout)

                        progressDialog.dismiss()
                    }

                } else {
                    binding.toplayout.setVisibility(View.GONE);
                    binding.refresh.visibility = View.VISIBLE
                    binding.secondLay.setVisibility(View.GONE);
                    progressDialog.dismiss()

                    Toast.makeText(this, it.messages+" Not available", Toast.LENGTH_SHORT).show()
                }


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
                    binding.tvSetmonthyear.setText("$monthtype $yeartype")
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
//            sendreqaddsettlelmnt()
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
                        searchmpgb()

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
                    searchmpgb()
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


    fun searchmpgb() {
        progressDialog.show()

        val setmonth = monthtype
        val setyear = yeartype
        val setapikey = SessionManager.getString(this, "apikey_other")
//        val setapikey = "41dlwk4a"
        val setbank = SessionManager.getString(this, "setbanktype")
//        val setbank = "MPGB"
        val setuserid = SessionManager.getString(this, "koidother")
//        val setuserid = "13041264"


        viewModelbanking.searchbympgb(getmonth = setmonth.toString(), getyear = setyear.toString() ,getapikey = setapikey.toString() ,getbank=setbank.toString() ,getuserid=setuserid.toString())

    }
}