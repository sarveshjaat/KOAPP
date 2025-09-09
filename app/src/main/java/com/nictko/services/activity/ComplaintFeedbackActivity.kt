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
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.snackbar.Snackbar
import com.nictko.services.R
import com.nictko.services.databinding.ActivityComplaintFeedbackBinding
import com.nictko.services.databinding.ActivityComplaintStatusViewBinding
import com.nictko.services.sharedprefrence.SessionManager
import com.nictko.services.viewmodel.AddComplaintReqViewModel

class ComplaintFeedbackActivity : AppCompatActivity() {
    private lateinit var binding: ActivityComplaintFeedbackBinding
    private lateinit var progressDialog: Dialog
    private val viewModel by viewModels<AddComplaintReqViewModel>()

    var feedbacktype: List<String> =
        mutableListOf(
            "Select Feedback Type",
            "Satisfied",
            "Not Satisfied",
            "Rude Behaviour on call",
            "Lack product knowledge",
            "Complaint closed without solution",
        )
    var selectfeedbacktype: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_complaint_feedback)
        val mobile = SessionManager.getString(this, "mobile")
        val koid = SessionManager.getString(this, "koid")
        val getstatename = SessionManager.getString(this, "statename")
        val getcomplaintid = SessionManager.getString(this, "complaintid")

        progressDialog = Dialog(this)
        val view = layoutInflater.inflate(R.layout.progress_dialog, null)
        progressDialog.setContentView(view)
        progressDialog.setCancelable(false)

        val adapterspiner = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            feedbacktype
        )
        adapterspiner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFeedback.setAdapter(adapterspiner)

        binding.spinnerFeedback.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedfeddback = parent.getItemAtPosition(position).toString()
                selectfeedbacktype = selectedfeddback

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        })

        viewModel.addfeedback_onlysbi.observe(this) {
            progressDialog.dismiss()
            if (it.responseStatus.equals("success")) {
                ///Log.e("sammm", "ty...." + it.messages)
                setmsgfeedback(it.messages)
            } else {
                progressDialog.dismiss()
                processError(it.messages)
            }
        }


        click()
    }

    private fun click() {

        binding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->

//            val ratingDescription = when (rating.toInt()) {
//                1 -> "Very Bad"
//                2 -> "Bad"
//                3 -> "Okay"
//                4 -> "Good"
//                5 -> "Excellent"
//                else -> "No Rating"
//            }
//            val ratingDescription1 = when (rating.toInt()) {
//                1 -> "\uD83D\uDE20 Very Bad"
//                2 -> "\uD83D\uDE1F Bad"
//                3 -> "\uD83D\uDE10 Okay"
//                4 -> "\uD83D\uDE10 Good"
//                5 -> "\uD83D\uDE0D Excellent"
//                else -> "\uD83E\uDD14 No Rating"
//            }
//             setratinggmsg =ratingDescription
//            binding.txtInputCmmessage.setText(ratingDescription1)

        }


        binding.profileImg.setOnClickListener {
            onBackPressed()
        }
        binding.btnSubffedback.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (isInternetConnected()) {

                    if (validateInputs()) {
                        progressDialog.show()
                        sendffedback()
                    }


                } else {
                    Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_SHORT)
                        .show()

                }
            } else {

                progressDialog.show()

                if (validateInputs()) {
                    progressDialog.show()
                    sendffedback()
                }

            }


        }

    }

    fun setmsgfeedback(msg: String?) {

        SweetAlertDialog(this@ComplaintFeedbackActivity, SweetAlertDialog.SUCCESS_TYPE)
            .setContentText(msg)
            .setConfirmClickListener {

                    sDialog ->
                finish()
                sDialog.dismissWithAnimation()
            }

            .show()

    }

    fun processError(msg: String?) {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        msg?.let { Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show() }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun validateInputs(): Boolean {
        var isValid = true
        // KO ID Validation
        if (selectfeedbacktype.isNullOrEmpty() || selectfeedbacktype == "Select Feedback Type") {
            Snackbar.make(binding.root, "Select Feedback Type", Snackbar.LENGTH_SHORT).show()
            isValid = false
        }

         else if (binding.ratingBar.rating == 0f) {
            Snackbar.make(binding.root, "Select Rating", Snackbar.LENGTH_SHORT).show()
            isValid = false


        }

        return isValid
    }

    fun sendffedback() {
        val setkoid = SessionManager.getString(this, "complaintid")
        val setrating = binding.ratingBar.rating
        //val setfeedbackmsg = binding.txtInputCmmessage.text.toString()
        //val setfeedbackmsg = binding.txtInputCmmessage.text.toString()
        val setfeedbackmsg = selectfeedbacktype.toString()

        setkoid?.let {
            viewModel.calladdfeedback_onlysbi(
                getkoid = it.toInt(),
                getrating = setrating.toString(),
                getfeedbackmsg = setfeedbackmsg.toString(),

                )
        };

    }


    private fun isInternetConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}