package com.nictko.services.activity

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
import com.nictko.services.databinding.ActivityOtpverifyBinding
import com.nictko.services.response.otpresponse.Data
import com.nictko.services.sharedprefrence.SessionManager
import com.nictko.services.viewmodel.OTPResendViewModel
import com.nictko.services.viewmodel.OTPVerifyViewModel

class OTPVerifyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpverifyBinding;
    private lateinit var progressDialog: Dialog
    private val viewModel by viewModels<OTPVerifyViewModel>()
    private val viewModelotpresend by viewModels<OTPResendViewModel>()
    private val counter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otpverify)
        val getkoidotp = SessionManager.getString(this, "koidotp")

        progressDialog = Dialog(this)
        val view = layoutInflater.inflate(R.layout.progress_dialog, null)
        progressDialog.setContentView(view)
        progressDialog.setCancelable(false)


        viewModel.otpResult.observe(this) {
            progressDialog.dismiss()
            Log.e("OTP Response", "Full response: $it")
            Log.e("OTP Message", "Message: ${it.messages}")
            Log.e("OTP Data", "Data: ${it.data}")

            if(it.messages.equals("Otp verified Successfull"))
            {
//                Otp verified Successfull
                it.data?.let { it1 -> processLogin(it1) }
            }
            else{
                progressDialog.dismiss()
                processError(it.messages)
            }

        }
        viewModelotpresend.otpresendResult.observe(this) {
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
        click()

    }


    private fun click()
    {
        noCount()
        binding.btnOtpverify.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (isInternetConnected()) {
                    if (validateInputs()) {
                        progressDialog.show()
                        verifyotp()
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
                    verifyotp()
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
                    callresendotp()
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
                callresendotp()
                binding.txtOtptimer.setVisibility(View.VISIBLE)
                //CALL API
            }
        }

    }

    fun callresendotp() {
        noCount()
        val setkoid = SessionManager.getString(this, "koidotp")
        viewModelotpresend.callotpresend(getkoid=setkoid.toString())

    }

    private fun isInternetConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
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

    fun verifyotp() {
        val setkoid = SessionManager.getString(this, "koidotp")
        val setotp = binding.otpview.text.toString()

        Log.e("sent ko id","yty"+setkoid);
        Log.e("sent ko otp","tyt"+setotp);


        viewModel.otpverifycall(getkoid = setkoid.toString(), getotp = setotp)

    }

    fun processLogin(getdata: Data) {
//        showToast("Login Successfull");
//        SessionManager.saveString(this ,"mobile",getdata.mobno!!)
//        SessionManager.saveString(this ,"koid", getdata.koid!!)
//        SessionManager.saveString(this ,"username", getdata.sdpName!!)
//        SessionManager.saveString(this ,"userrole", getdata.sdpHaddr!!)
//        SessionManager.saveString(this ,"sdplocation", getdata.sdpLoc!!)
//        SessionManager.saveString(this ,"statename", getdata.stname!!)
//        SessionManager.saveString(this ,"divisionname", getdata.divname!!)
//        SessionManager.saveString(this ,"dname", getdata.dtname!!)
//        SessionManager.saveString(this ,"limithold", getdata.limitHold!!.toString())
//        SessionManager.saveString(this ,"SETTYPELOGIN","1")

        SessionManager.saveString(this, "mobile", getdata.mobno ?: "")
        SessionManager.saveString(this, "koid", getdata.koid ?: "")
        SessionManager.saveString(this, "username", getdata.sdpName ?: "")
        SessionManager.saveString(this, "userrole", getdata.sdpHaddr ?: "")
        SessionManager.saveString(this, "sdplocation", getdata.sdpLoc ?: "")
        SessionManager.saveString(this, "statename", getdata.stname ?: "")
        SessionManager.saveString(this, "divisionname", getdata.divname ?: "")
        SessionManager.saveString(this, "dname", getdata.dtname ?: "")
        SessionManager.saveString(this, "limithold", getdata.limitHold?.toString() ?: "0")
        SessionManager.saveString(this, "SETTYPELOGIN", "1")


        val intent = Intent(this, MainDashBoardActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun processresendotp(msg: String?) {
        Snackbar.make(binding.root, msg.toString(), Snackbar.LENGTH_SHORT).show()

    }
    fun processError(msg: String?) {
        Snackbar.make(binding.root, msg.toString(), Snackbar.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity();
        finish();
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
}