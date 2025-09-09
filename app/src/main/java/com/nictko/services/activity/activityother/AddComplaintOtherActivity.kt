package com.nictko.services.activity.activityother

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.snackbar.Snackbar
import com.nictko.services.R
import com.nictko.services.databinding.ActivityAddComplaintOtherBinding
import com.nictko.services.databinding.ActivityAddComplaintRequestBinding
import com.nictko.services.sharedprefrence.SessionManager
import com.nictko.services.viewmodel.AddComplaintReqViewModel

class AddComplaintOtherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddComplaintOtherBinding
    private lateinit var progressDialog: Dialog
    private val viewModel by viewModels<AddComplaintReqViewModel>()
    var complaintnature: List<String> =
        mutableListOf(
            "Select Complaint Type",
            "Peri Service Installation",
            "Biometric Device Installation",
            "MATM/PINPAD Installation",
            "Passbook Printer Installation",
            "RD Service Installation",
            "Microsoft Edge setting",
            "Password Reset ( login & Transaction )",
            "Others"
        )
    var selectcomplatintype: String? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_complaint_other)
        val getmobile_other = SessionManager.getString(this, "usermobile_other")
        val getuserkoid_other = SessionManager.getString(this, "userId_other")
        val getkoid_cspcodeother = SessionManager.getString(this, "csp_code_other")
        val getkoid_username_other = SessionManager.getString(this, "username_other")
        val getbanktype = SessionManager.getString(this, "setbanktype")
        val getallbankdistrict = SessionManager.getString(this, "allbankother_district")
        val getallbankstate = SessionManager.getString(this, "allbankother_state")



        binding.txtInputUname.setText(getkoid_username_other)
        binding.txtInputUmobile.setText(getmobile_other)
        binding.txtInputServiceid.setText(getuserkoid_other)
        progressDialog = Dialog(this)
        val view = layoutInflater.inflate(R.layout.progress_dialog, null)
        progressDialog.setContentView(view)
        progressDialog.setCancelable(false)

        val adapterspiner = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            complaintnature
        )
        adapterspiner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCompalintnature.setAdapter(adapterspiner)

        binding.spinnerCompalintnature.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectednature = parent.getItemAtPosition(position).toString()
                selectcomplatintype = selectednature

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        })



        viewModel.addcomplaintrespResult_other.observe(this) {
            progressDialog.dismiss()

            if (it.messages.equals("Somrthing went wrong, try after sometime.")) {
                processError(it.messages)

            }
//            if (it.responseStatus.equals("success")) {
//                Log.e("sammm", "ty...." + it.messages)
//                settlementconfirm(it.messages)
//            }
            else {
                progressDialog.dismiss()
                settlementconfirm(it.messages)

            }
        }
        click()
    }

    private fun click() {
        binding.profileImg.setOnClickListener {
            onBackPressed()
        }
        binding.btnSubcomplaint.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (isInternetConnected()) {
                    if (validateInputs()) {
                        progressDialog.show()
                        sendreqcomplaint_other()
                    }
                } else {
                    Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_SHORT)
                        .show()

                }
            } else {

                if (validateInputs()) {
                    progressDialog.show()
                    sendreqcomplaint_other()
                }

            }

        }

    }


    private fun isInternetConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun validateInputs(): Boolean {
        var isValid = true
        // KO ID Validation

        if (selectcomplatintype.isNullOrEmpty() || selectcomplatintype == "Select Complaint Type") {
            Snackbar.make(binding.root, "Select Complaint Type", Snackbar.LENGTH_SHORT).show()
            isValid = false
        }


         else if (binding.txtInputCmmessage.text.isNullOrEmpty()) {
//            showToast("Enter Message ")
            Snackbar.make(binding.root, "Enter Message", Snackbar.LENGTH_SHORT).show()

            isValid = false
        }

        return isValid
    }

    fun sendreqcomplaint_other() {
        val setstatename = SessionManager.getString(this, "allbankother_state")
        val setdivisionname =""
        val setdistricname = SessionManager.getString(this, "allbankother_district")
        val setuname = binding.txtInputUname.text.toString()
        val setumobile = binding.txtInputUmobile.text.toString()
        val setumobile_alternet = binding.txtInputUmobileAlter.text.toString()
        val setserviceid = binding.txtInputServiceid.text.toString()
        val setservicetype = "Banking"
        val setbanktyp = SessionManager.getString(this, "setbanktype")
        val setinputmsg = binding.txtInputCmmessage.text.toString()

        viewModel.calladdcompaintreqdata_otherbank(
            getstatename = setstatename.toString(),
            getdivisionname = setdivisionname,
            getdistricname = setdistricname.toString(),
            getuname = setuname,
            getumobile = setumobile,
            getumobile_alternet = setumobile_alternet,
            getserviceid = setserviceid,
            getinputmsg = setinputmsg,
            getservicename = setservicetype,
            getbankname = setbanktyp.toString(),
            getcategoryname = selectcomplatintype.toString(),
            getcspcategoryname = ""

        );

    }

    fun settlementconfirm(msg: String?) {
//        msg?.let { Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show() }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

        SweetAlertDialog(this@AddComplaintOtherActivity, SweetAlertDialog.SUCCESS_TYPE)
            .setContentText(msg)
            .setConfirmClickListener {

                    sDialog ->
                finish()
                sDialog.dismissWithAnimation()
            }

            .show()
        //finish()

    }
    fun processError(msg: String?) {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        msg?.let { Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show() }

    }

}