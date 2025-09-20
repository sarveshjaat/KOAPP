package com.nictko.services.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.AllCaps
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.nictko.services.R
import com.nictko.services.activity.activityother.OtpVerifyOtherActivity
import com.nictko.services.databinding.ActivityLoginBinding
import com.nictko.services.sharedprefrence.SessionManager
import com.nictko.services.viewmodel.LoginViewModel


class LoginActivity : AppCompatActivity() {


    ///sarveshnewsttaus bffhfhgit

    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var progressDialog: Dialog
    var bankList: List<String> =
        mutableListOf(
            "Select Bank",
            "SBI",
            "BOI",
            "BOB",
            "UBIN",
            "PNB",
            "BOM",
            "BUPGB",
            "MGB",
            "BGGB",
            "CRGB",
            "MPGB"
        )


    var selectbanktype: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val token = SessionManager.getToken(this)
        progressDialog = Dialog(this)
        val view = layoutInflater.inflate(R.layout.progress_dialog, null)
        progressDialog.setContentView(view)
        progressDialog.setCancelable(false)

        val adapterspiner = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            bankList
        )
        adapterspiner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerBank.setAdapter(adapterspiner)

        binding.spinnerBank.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedBank = parent.getItemAtPosition(position).toString()
                selectbanktype = selectedBank

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        })


        binding.btnLogin.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (isInternetConnected()) {
                    if (validateInputs()) {
                        progressDialog.show()


                        if (selectbanktype.equals("SBI")) {
                            doLogin(selectbanktype.toString())
                        } else {
                            doLoginother(selectbanktype.toString())
                        }


                    }
                } else {
                    Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_SHORT)
                        .show()

                }
            } else {
                if (validateInputs()) {
                    progressDialog.show()

                    if (selectbanktype.equals("SBI")) {
                        doLogin(selectbanktype.toString())

                    } else {
                        doLoginother(selectbanktype.toString())

                    }
                }
            }

        }
        viewModel.loginResult.observe(this) {
            progressDialog.dismiss()
            if (it.messages.equals("Invalid credential.")) {
                progressDialog.dismiss()
                processError(it.messages)
            } else {
                processLogin(it.data.toString())
            }

        }

        viewModel.loginResultother.observe(this) {
            progressDialog.dismiss()
            if (it.messages.equals("Invalid credential.")) {
                progressDialog.dismiss()
                processError(it.messages)
            } else {
                processLoginother(it.data.toString())
            }

        }
        binding.txtInputUserid.setFilters(arrayOf<InputFilter>(AllCaps()))

    }

    fun doLogin(getbanktype: String) {
        val setuserid = binding.txtInputUserid.text.toString()
        val setpwd = binding.txtPass.text.toString()
        val setbanktype = getbanktype.trim()
        SessionManager.saveString(this, "setbanktype", getbanktype.trim())
        viewModel.loginUser(getuserid = setuserid, getpwd = setpwd)
    }

    fun doLoginother(getbanktype: String) {
        val setuserid = binding.txtInputUserid.text.toString()
        val setpwd = binding.txtPass.text.toString()
        val setbanktype = getbanktype.trim()
        SessionManager.saveString(this, "setbanktype", getbanktype.trim())
        viewModel.loginUserother(getuserid = setuserid, getpwd = setpwd, getbanktype = setbanktype)
    }

    fun processLogin(getdatakoid: String) {
        showToast("Welcome to Nict KO");
        //showToast("chb")

        SessionManager.saveString(this, "koidotp", getdatakoid)
        val intent = Intent(this, OTPVerifyActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun processLoginother(getdatakoid: String) {
        showToast("Welcome to Nict KO");

//        SessionManager.saveString(this ,"mobile",getdata.mobno!!)
//        SessionManager.saveString(this ,"koid", getdata.koid!!)
//        SessionManager.saveString(this ,"username", getdata.sdpName!!)
//        SessionManager.saveString(this ,"userrole", getdata.sdpHaddr!!)
//        SessionManager.saveString(this ,"sdplocation", getdata.sdpLoc!!)
//        SessionManager.saveString(this ,"userid", binding.txtInputUserid.text.toString())
//        SessionManager.saveString(this ,"statename", getdata.stname!!)
//        SessionManager.saveString(this ,"divisionname", getdata.divname!!)
//        SessionManager.saveString(this ,"dname", getdata.dtname!!)
//        SessionManager.saveString(this ,"limithold", getdata.limitHold!!.toString())
        SessionManager.saveString(this, "koidothernew", getdatakoid)
        Log.e("", "getdatakoid" + getdatakoid);
        val intent = Intent(this, OtpVerifyOtherActivity::class.java)
        startActivity(intent)
        finish()
    }


    fun processError(msg: String?) {
        Snackbar.make(binding.root, "Invalid Login details", Snackbar.LENGTH_SHORT).show()
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        if (selectbanktype.isNullOrEmpty() || selectbanktype == "Select Bank") {
            Snackbar.make(binding.root, "Select Bank Type", Snackbar.LENGTH_SHORT).show()
            isValid = false
        } else if (binding.txtInputUserid.text.isNullOrEmpty()) {
            Snackbar.make(binding.root, "User ID is required", Snackbar.LENGTH_SHORT).show()

            isValid = false
        } else if (binding.txtPass.text.isNullOrEmpty()) {
            Snackbar.make(binding.root, "Password is required", Snackbar.LENGTH_SHORT).show()
            isValid = false
        }
        return isValid
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity();
        finish();
    }

    private fun isInternetConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }


}