package com.nictko.services.activity

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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.snackbar.Snackbar
import com.nictko.services.R
import com.nictko.services.databinding.ActivityAddComplaintRequestBinding
import com.nictko.services.sharedprefrence.SessionManager
import com.nictko.services.viewmodel.AddComplaintReqViewModel


class AddComplaintRequest : AppCompatActivity() {
    private lateinit var binding: ActivityAddComplaintRequestBinding
    private val viewModel by viewModels<AddComplaintReqViewModel>()
    private var getcspCategorye: String? = null
//addcomplain
    private lateinit var progressDialog: Dialog
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_complaint_request)
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

        binding.txtInputUname.setText(getuname)
        binding.txtInputUmobile.setText(mobile)
        binding.txtInputServiceid.setText(koid)

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



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (isInternetConnected()) {
                progressDialog.show()
                getcspcateghory()

            } else {
                Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_SHORT)
                    .show()
            }
        } else {

            progressDialog.show()
            getcspcateghory()


        }

        viewModel.cspcategory_resultrep.observe(this) {
            progressDialog.dismiss()
            if (it.status == 200) {
//                Log.e("sammm", "ty...." + it.messages)
                getcspCategorye = it.cspCategory
                Log.e("getcspCategorye", ".li.." + getcspCategorye);

                // settlementconfirm(it.messages)

            } else {
                progressDialog.dismiss()
//                processError_cspcatgeory(it.messages)
            }
        }

        binding.btnSubcomplaint.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (isInternetConnected()) {
                    if (validateInputs()) {
                        progressDialog.show()
                        sendreqcomplaint()
                    }
                } else {
                    Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_SHORT)
                        .show()

                }
            } else {

                if (validateInputs()) {
                    progressDialog.show()
                    sendreqcomplaint()
                }

            }

        }



        viewModel.addcomplaintrespResult.observe(this) {
            progressDialog.dismiss()
            if (it.messages.equals("Somrthing went wrong, try after sometime.")) {
                Log.e("sammm", "ty...." + it.messages)
                processError(it.messages)
            } else {
                progressDialog.dismiss()
                settlementconfirm(it.messages)
            }
        }
        binding.profileImg.setOnClickListener {
            onBackPressed()
        }


    }

    fun settlementconfirm(msg: String?) {

        SweetAlertDialog(this@AddComplaintRequest, SweetAlertDialog.SUCCESS_TYPE)
            .setContentText(msg)
            .setConfirmClickListener { sDialog ->
                finish()
                sDialog.dismissWithAnimation()
            }
            .show()

    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun processError(msg: String?) {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        msg?.let { Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show() }

    }

    fun processError_cspcatgeory(msg: String?) {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        msg?.let { Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show() }

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
            Snackbar.make(binding.root, "Enter Any Message", Snackbar.LENGTH_SHORT).show()

            isValid = false
        }

        return isValid
    }

    fun getcspcateghory() {
        val setkoid = SessionManager.getString(this, "koid")
        viewModel.callcspcategory(getkoid = setkoid.toString());
    }


    fun sendreqcomplaint() {

        val setstatename = SessionManager.getString(this, "statename")
        val setdivisionname = SessionManager.getString(this, "divisionname")
        val setdistricname = SessionManager.getString(this, "dname")
        val setuname = binding.txtInputUname.text.toString()
        val setumobile = binding.txtInputUmobile.text.toString()
        val setumobilealt = binding.txtInputUmobileAlt.text.toString()
        val setserviceid = binding.txtInputServiceid.text.toString()
        val setinputmsg = binding.txtInputCmmessage.text.toString()
        val setbankname = SessionManager.getString(this, "setbanktype")

        Log.e("getcspCategorye", "...." + getcspCategorye.toString())

        viewModel.calladdcompaintreqdata(
            getstatename = setstatename.toString(),
            getdivisionname = setdivisionname.toString(),
            getdistricname = setdistricname.toString(),
            getuname = setuname,
            getumobile = setumobile,
            getumobilealt = setumobilealt,
            getserviceid = setserviceid,
            getinputmsg = setinputmsg,
            getservicename = "Banking",
            getbankname = setbankname.toString(),
            getcompCat = selectcomplatintype.toString(),
            getcspgoryname = getcspCategorye.toString()

        );
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
}