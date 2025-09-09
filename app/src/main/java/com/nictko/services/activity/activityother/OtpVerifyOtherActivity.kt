package com.nictko.services.activity.activityother

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.nictko.services.R
import com.nictko.services.databinding.ActivityOtpVerifyOtherBinding
import com.nictko.services.responseother.otpverifyresp.Data
import com.nictko.services.sharedprefrence.SessionManager
import com.nictko.services.viewmodel.OTPResendViewModel
import com.nictko.services.viewmodel.OTPVerifyViewModel

class OtpVerifyOtherActivity : AppCompatActivity() {
    private lateinit var progressDialog: Dialog
    private lateinit var binding: ActivityOtpVerifyOtherBinding;
    private val viewModel by viewModels<OTPVerifyViewModel>()
    private val viewModelotpresend by viewModels<OTPResendViewModel>()

    private val counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  enableEdgeToEdge()
       // setContentView(R.layout.activity_otp_verify_other)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp_verify_other)

        val getkoid_other = SessionManager.getString(this, "koidother")

        val getbanktype = SessionManager.getString(this, "setbanktype")

        progressDialog = Dialog(this)
        val view = layoutInflater.inflate(R.layout.progress_dialog, null)
        progressDialog.setContentView(view)
        progressDialog.setCancelable(false)
        click()

        viewModel.otpResult_other.observe(this) {
            Log.e("gjgj","vj"+it.data)
            progressDialog.dismiss()
            if(it.messages.equals("Otp verified Successfull"))
            {
                Log.e("gjgj","vj"+it.data)

                it.data?.let { it1 -> otpverified(it1) }

            }
            else{
                Log.e("msg","msg....."+it.messages)
                Log.e("gjgjfgfgf","vj"+it.data)

                progressDialog.dismiss()
                processError(it.messages)
            }

        }
        viewModelotpresend.otpresendResult_other.observe(this) {
            progressDialog.dismiss()
            if(it.responseStatus.equals("success"))
            {
                processresendotp(it.messages)
            }
            else{
                progressDialog.dismiss()
                processError(it.messages)
            }

        }

    }
    private fun click()
    {
        noCount()

        binding.btnOtpverify.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (isInternetConnected()) {
                    if (validateInputs()) {
                        progressDialog.show()
                        verifyotp_other()
                        //CALL API
                    }
                }
                else{
                    Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_SHORT).show()
                }
            }
            else{
                if (validateInputs()) {
                    progressDialog.show()
                    verifyotp_other()
                    //CALL API
                }
            }

        }

        binding.txtResendotp.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (isInternetConnected()) {
                    binding.txtResendotp.setVisibility(View.GONE)
                    binding.btnOtpverify.setVisibility(View.VISIBLE)
                    progressDialog.show()
                    callresendotp_other()
                    binding.txtOtptimer.setVisibility(View.VISIBLE)
                }
                else{
                    Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_SHORT).show()
                }
            }
            else{
                binding.txtResendotp.setVisibility(View.GONE)
                binding.btnOtpverify.setVisibility(View.VISIBLE)
                progressDialog.show()
                callresendotp_other()
                binding.txtOtptimer.setVisibility(View.VISIBLE)
                //CALL API
            }
        }


    }

    fun otpverified(getdata: Data) {
        val setkoid_other = SessionManager.getString(this, "koidothernew")
        SessionManager.saveString(this ,"koidother",setkoid_other?: "")
        SessionManager.saveString(this ,"userId_other",getdata.userId?: "")
        SessionManager.saveString(this ,"username_other", getdata.userName ?: "")
        SessionManager.saveString(this ,"usermobile_other", getdata.mobileNo?: "")
        SessionManager.saveString(this ,"csp_code_other", getdata.cspCode?: "")
        SessionManager.saveString(this ,"apikey_other", getdata.apikey?: "")
        SessionManager.saveString(this ,"SETTYPELOGIN","2")

        val intent = Intent(this, DashBordOtherMainActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun processresendotp(msg: String?) {
        Snackbar.make(binding.root, msg.toString(), Snackbar.LENGTH_SHORT).show()

    }
    fun processError(msg: String?) {
        Log.e("msg","msg.....");
        Snackbar.make(binding.root, msg.toString(), Snackbar.LENGTH_SHORT).show()
    }
    fun verifyotp_other() {
        val setkoid_other = SessionManager.getString(this, "koidothernew")
        val setotp_other = binding.otpview.text.toString()
        val setbanktype_other = SessionManager.getString(this, "setbanktype")
        viewModel.otpverifycallother(getkoid = setkoid_other.toString(), getotp = setotp_other ,getbanktype = setbanktype_other.toString())

    }
    fun callresendotp_other() {
        noCount()
        val setkoid_other = SessionManager.getString(this, "koidothernew")
        val setbanktype_other = SessionManager.getString(this, "setbanktype")


        viewModelotpresend.callotpresend_other(getkoid_other=setkoid_other.toString() ,getbanktype=setbanktype_other.toString())

    }
    private fun validateInputs(): Boolean {

        var isValid = true
        if (binding.otpview.text.isNullOrEmpty()) {
//            showToast("Enter Limit Amount")
            Snackbar.make(binding.root, "Enter OTP", Snackbar.LENGTH_SHORT).show()

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
    private fun noCount() {
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.txtOtptimer.setText(counter.toString())
                binding.txtOtptimer.setText(getString(R.string.resend_OTP_in) + " " + millisUntilFinished / 1000 + " " + "sec")
            }
            override fun onFinish() {
                binding.txtOtptimer.setVisibility(View.GONE)
                binding.txtResendotp.setVisibility(View.VISIBLE)
                binding.btnOtpverify.setVisibility(View.GONE)
            }
        }.start()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity();
        finish();
    }
}