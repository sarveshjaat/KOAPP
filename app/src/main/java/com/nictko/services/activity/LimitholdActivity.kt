package com.nictko.services.activity

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.snackbar.Snackbar
import com.nictko.services.R
import com.nictko.services.databinding.ActivityLimitholdBinding
import com.nictko.services.sharedprefrence.SessionManager
import com.nictko.services.viewmodel.AddLimitHoldViewModel


class LimitholdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLimitholdBinding
    private lateinit var progressDialog: Dialog

    private val viewModel by viewModels<AddLimitHoldViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_limithold)
        val getlimithold = SessionManager.getString(this, "limithold")
        binding.txtInputLimithold.setText(getlimithold)

        progressDialog = Dialog(this)
        val view = layoutInflater.inflate(R.layout.progress_dialog, null)
        progressDialog.setContentView(view)
        progressDialog.setCancelable(false)
        click()
        viewModel.addlimiholdrespResult.observe(this) {
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

    }

    private fun click()
    {
        binding.backImg.setOnClickListener {
          finish()
        }

        binding.btnLimitholdsub.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (isInternetConnected()) {
                    if (validateInputs()) {
                        progressDialog.show()
                        sendlimithold()
                    }
                }
                else{
                    Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_SHORT).show()

                }
            }
            else{
                if (validateInputs()) {
                    progressDialog.show()
                    sendlimithold()
                }
            }


        }
    }
    private fun validateInputs(): Boolean {
        var isValid = true
        if (binding.txtInputLimithold.text.isNullOrEmpty()) {
//            showToast("Enter Limit Amount")
            Snackbar.make(binding.root, "Enter Limit Amount", Snackbar.LENGTH_SHORT).show()

            isValid = false
        }
        return isValid
    }
    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun sendlimithold() {
        val setkoid= SessionManager.getString(this, "koid");
        val setlimitamount = binding.txtInputLimithold.text.toString()

        viewModel.calllimitholdadd(getlimitamount=setlimitamount.toInt(),getkoid= setkoid.toString());
    }

    fun settlementconfirm(msg: String?) {

        SessionManager.saveString(this ,"limithold", binding.txtInputLimithold.text.toString())

        SweetAlertDialog(this@LimitholdActivity, SweetAlertDialog.SUCCESS_TYPE)
        .setContentText("You have Succesfully Update Limit Hold ")
            .setConfirmClickListener {

                    sDialog -> finish()
                sDialog.dismissWithAnimation()
            }

            .show()

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