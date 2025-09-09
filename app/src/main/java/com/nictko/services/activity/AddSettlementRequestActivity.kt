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
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.activity.viewModels
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.snackbar.Snackbar

import com.nictko.services.R
import com.nictko.services.databinding.ActivityAddSettlementRequestBinding
import com.nictko.services.sharedprefrence.SessionManager
import com.nictko.services.viewmodel.AddSettlementReqViewModel
import com.nictko.services.viewmodel.VersionDetailsViewModel

class AddSettlementRequestActivity : AppCompatActivity()  ,AdapterView.OnItemSelectedListener{
    private val viewModel by viewModels<AddSettlementReqViewModel>()

    private lateinit var binding: ActivityAddSettlementRequestBinding
    private val courses = arrayOf("Select Type","Amount", "Full Settlement", "No Settlement")
    var setaccouttype =""
    private lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_settlement_request)
        val getmobile = SessionManager.getString(this, "mobile")
        val getkoid = SessionManager.getString(this, "koid")
        val getusername = SessionManager.getString(this, "username")
        val getuserrole = SessionManager.getString(this, "userrole")
        val getuserid = SessionManager.getString(this, "userid")
        binding.txtInputKoid.setText(getkoid)
        binding.txtMobileno.setText(getmobile)

        progressDialog = Dialog(this)
        val view = layoutInflater.inflate(R.layout.progress_dialog, null)
        progressDialog.setContentView(view)
        progressDialog.setCancelable(false)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            courses
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = this

        viewModel.addsetlemetreqResult.observe(this) {
            progressDialog.dismiss()
            if(it.responseStatus.equals("success"))
            {
                Log.e("sammm","ty...."+it.messages)
                settlementconfirm(it.messages)
            }
            else{
                progressDialog.dismiss()
                processError(it.messages)
            }
        }

        binding.btnSetmentsub.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (isInternetConnected()) {
                    if (validateInputs()) {
                        progressDialog.show()
                        sendreqaddsettlelmnt()
                    }
                }
                else{
                    Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_SHORT).show()

                }
            }
            else{

                if (validateInputs()) {
                    progressDialog.show()
                    sendreqaddsettlelmnt()
                }

            }
        }
        binding.profileImg.setOnClickListener {
            onBackPressed()
        }



    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // Display a toast with the selected course name
//        Toast.makeText(requireContext(), courses[position], Toast.LENGTH_SHORT).show()
        if(courses[position].equals("Full Settlement"))
        {
            setaccouttype="Full Settlement"
            binding.txtAmount.setCursorVisible(false);
            binding.txtAmount.setClickable(false)
            binding.txtAmount.setEnabled(false);
            binding.txtAmount.setText("1")
        }
        else if(courses[position].equals("No Settlement"))
        {
            setaccouttype="No Settlement"
            binding.txtAmount.setCursorVisible(false);
            binding.txtAmount.setClickable(false)
            binding.txtAmount.setEnabled(false);
            binding.txtAmount.setText("0")

        }
        else if(courses[position].equals("Amount"))
        {
            setaccouttype="Amount"
            binding.txtAmount.setCursorVisible(true);
            binding.txtAmount.setClickable(true)
            binding.txtAmount.setEnabled(true);
            binding.txtAmount.setText("")

        }
        else
        {
            binding.txtAmount.setCursorVisible(false);
            binding.txtAmount.setClickable(false)
            binding.txtAmount.setEnabled(false);
            binding.txtAmount.setText("")
        }

    }

    override   fun onNothingSelected(parent: AdapterView<*>?) {
        // Do nothing if no item is selected
    }

    private fun validateInputs(): Boolean {
        var isValid = true
        // KO ID Validation
        if (binding.txtAmount.text.isNullOrEmpty()) {
            Snackbar.make(binding.root, "Enter Amount", Snackbar.LENGTH_SHORT).show()
            isValid = false
        }
        else if (binding.txtRemark.text.isNullOrEmpty()) {
//            showToast("Add Remark")
            Snackbar.make(binding.root, "Add Remark", Snackbar.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }
    fun settlementconfirm(msg: String?) {

        if(msg.equals("Settlement req already submitted."))
        {
            SweetAlertDialog(this@AddSettlementRequestActivity, SweetAlertDialog.WARNING_TYPE)
                .setContentText(msg)
                .setConfirmClickListener {

                        sDialog -> finish()
                    sDialog.dismissWithAnimation()
                }

                .show()
        }
        else{

            SweetAlertDialog(this@AddSettlementRequestActivity, SweetAlertDialog.SUCCESS_TYPE)
                .setContentText(msg)
                .setConfirmClickListener {

                        sDialog -> finish()
                    sDialog.dismissWithAnimation()
                }

                .show()

        }


    }




    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun processError(msg: String?) {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        msg?.let { Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show() }

    }
    fun sendreqaddsettlelmnt() {
        val setkoid = binding.txtInputKoid.text.toString()
        val setmobno = binding.txtMobileno.text.toString()
        val setamount = binding.txtAmount.text.toString()
        val setremark = binding.txtRemark.text.toString()
        val set_sttype = setaccouttype
        viewModel.calladdsettlement(getkoid = setkoid, getmobno = setmobno ,getacctype= setaccouttype,getamount=setamount ,getuserremark=setremark ,getsettype=set_sttype)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    private fun isInternetConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}