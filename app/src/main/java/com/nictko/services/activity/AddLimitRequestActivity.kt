package com.nictko.services.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.snackbar.Snackbar
import com.nictko.services.R
import com.nictko.services.api.RetrofitClient
import com.nictko.services.databinding.ActivityAddLimitRequestBinding
import com.nictko.services.sharedprefrence.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class AddLimitRequestActivity : AppCompatActivity() , AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityAddLimitRequestBinding
    private val courses = arrayOf("Select Mode","By Cash", "By Cheque", "By Internet Banking")
    var setpaymodetype =""
    private var selectedImagePath: String? = null
    private var selectedImagePath1: String? = null
    private var selectedImageBitmap: Bitmap? = null
    private lateinit var progressDialog: Dialog
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_limit_request)
        val getmobile = SessionManager.getString(this, "mobile")
        val getkoid = SessionManager.getString(this, "koid")
        val getusername = SessionManager.getString(this, "username")
        val getuserrole = SessionManager.getString(this, "userrole")
        val getsdplocation = SessionManager.getString(this, "sdplocation")
        val setstatename = SessionManager.getString(this, "statename")
        val setdivisionname = SessionManager.getString(this, "divisionname")
        val setdistricname = SessionManager.getString(this, "dname")

        Log.e("...location","..getsdplocation.."+SessionManager.getString(this, "sdplocation"));
        Log.e("...sam","..statename.."+SessionManager.getString(this, "statename"));
        Log.e("...sam","..divisionname.."+SessionManager.getString(this, "divisionname"));
        Log.e("...sam","..dname.."+SessionManager.getString(this, "dname"));

        val getuserid = SessionManager.getString(this, "userid")

        val sdf = SimpleDateFormat("yyyy-MM-dd ")
        val currentDate = sdf.format(Date())
        System.out.println(" C DATE is  "+currentDate)

        progressDialog = Dialog(this)
        val view = layoutInflater.inflate(R.layout.progress_dialog, null)
        progressDialog.setContentView(view)
        progressDialog.setCancelable(false)

        binding.txtInputKoid.setText(getkoid)
        binding.txtSdpname.setText(getusername)

       // binding.txtLocation.setText(getsdplocation)
        binding.txtLocation.setText(setstatename)
        binding.txtMobileno.setText(getmobile)
        binding.txtDddate.setText(currentDate)
        binding.txtCheckdate.setText(currentDate)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            courses
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerModofpay.adapter = adapter
        binding.spinnerModofpay.onItemSelectedListener = this

        binding.profileImg.setOnClickListener {
            onBackPressed()
        }

        binding.txtDddate.setOnClickListener {

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our edit text.
//                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
//                    val dat = (year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth.toString())
//                    binding.txtDddate.setText(dat)

                    var date = "1"

                    //
                    val finalmonth = monthOfYear + 1
                    val finalmonthsum: String
                    if (finalmonth < 10) {
                        finalmonthsum = "0$finalmonth"
                        Log.e("final month e", "" + finalmonthsum + "")
                    } else {
                        finalmonthsum = finalmonth.toString()
                        Log.e("final month ELESE", "" + finalmonthsum + "")
                    }

                    if (dayOfMonth < 10) {
                        date = "0$dayOfMonth"
                        Log.e("if monthOfYear", "" + finalmonth)
                        //                                    binding.tvBusddate.setText(date + "/" + (monthOfYear + 1) + "/" + year);
                        binding.txtDddate.setText("$year-$finalmonthsum-$date")
                    } else {
//                                    binding.tvBusddate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                                    binding.tvBusddate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        Log.e("if monthOfYear else ", "" + finalmonth)

                        binding.txtDddate.setText("$year-$finalmonthsum-$dayOfMonth")

                    }


                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            // at last we are calling show
            // to display our date picker dialog.
            datePickerDialog.show()
        }
        binding.txtCheckdate.setOnClickListener {

            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our edit text.
//                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
//                    val dat = (year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth.toString())
//
//                    binding.txtCheckdate.setText(dat)

                    var date = "1"

                    //
                    val finalmonth = monthOfYear + 1
                    val finalmonthsum: String
                    if (finalmonth < 10) {
                        finalmonthsum = "0$finalmonth"
                        Log.e("final month e", "" + finalmonthsum + "")
                    } else {
                        finalmonthsum = finalmonth.toString()
                        Log.e("final month ELESE", "" + finalmonthsum + "")
                    }

                    if (dayOfMonth < 10) {
                        date = "0$dayOfMonth"
                        Log.e("if monthOfYear", "" + finalmonth)
                        //                                    binding.tvBusddate.setText(date + "/" + (monthOfYear + 1) + "/" + year);
                        binding.txtCheckdate.setText("$year-$finalmonthsum-$date")
                    } else {
//                                    binding.tvBusddate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                                    binding.tvBusddate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        Log.e("if monthOfYear else ", "" + finalmonth)

                        binding.txtCheckdate.setText("$year-$finalmonthsum-$dayOfMonth")

                    }


                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            // at last we are calling show
            // to display our date picker dialog.
            datePickerDialog.show()
        }
        binding.btnUploadimg.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // For Android 10 and above
                imageChooser()
            } else {
                // For Android 9 and below
                openGallery()
            }

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (isInternetConnected()) {

                binding.btnAddlimitreq.setOnClickListener {

                    val koid = binding.txtInputKoid.text.toString()
                    val sdpname = binding.txtSdpname.text.toString()
                    val tvlocation = binding.txtLocation.text.toString()
                    val tvmobno = binding.txtMobileno.text.toString()
                    val tvdepdate = binding.txtDddate.text.toString()
                    val tvamount = binding.txtAmount.text.toString()
                    val paymode =setpaymodetype
                    val tvbname = binding.txtBname.text.toString()
                    val tvbcode = binding.txtBcode.text.toString()
                    val tvckeckdate = binding.txtCheckdate.text.toString()
                    val tvcheckno = binding.txtChecknumber.text.toString()
                    val tvcheckbname = binding.txtCheckbankname.text.toString()
                    val tvibno = binding.txtIbnumber.text.toString()

                    if(paymode.equals("By Cash"))
                    {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            // For Android 10 and above

                            if (koid.isEmpty() || sdpname.isEmpty() ||tvmobno.isEmpty() || tvdepdate.isEmpty() || tvamount.isEmpty()||tvbname.isEmpty()||tvbcode.isEmpty()) {
                                Snackbar.make(binding.root, "Please fill all fields", Snackbar.LENGTH_SHORT).show()
                                return@setOnClickListener
                            }
                            else if (selectedImagePath.isNullOrEmpty()) {
                                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
                                return@setOnClickListener
                            }

                            else
                            {
                                uploadData("By Cash" ,selectedImagePath!!, koid, sdpname, tvlocation ,tvmobno ,tvdepdate ,tvamount ,"By Cash" ,tvbname,tvbcode ,"" ,"" ,"" ,"")

                            }
                        } else {

                            if (koid.isEmpty() || sdpname.isEmpty() ||tvmobno.isEmpty() || tvdepdate.isEmpty() || tvamount.isEmpty()||tvbname.isEmpty()||tvbcode.isEmpty()) {
                                Snackbar.make(binding.root, "Please fill all fields", Snackbar.LENGTH_SHORT).show()
                                return@setOnClickListener
                            }
                            else if (selectedImagePath1.isNullOrEmpty()) {
                                Toast.makeText(this, "Please select an image below 9 ", Toast.LENGTH_SHORT).show()
                                return@setOnClickListener
                            }

                            else
                            {
                                uploadData("By Cash" ,selectedImagePath1!!, koid, sdpname, tvlocation ,tvmobno ,tvdepdate ,tvamount ,"By Cash" ,tvbname,tvbcode ,"" ,"" ,"" ,"")

                            }

                            // For Android 9 and below
                        }



                    }
                    else if(paymode.equals("By Cheque"))
                    {

                        if (koid.isEmpty() || sdpname.isEmpty() || tvmobno.isEmpty() || tvdepdate.isEmpty() || tvamount.isEmpty()||tvckeckdate.isEmpty()||tvcheckno.isEmpty()||tvcheckbname.isEmpty()) {
                            Snackbar.make(binding.root, "Please fill all fields", Snackbar.LENGTH_SHORT).show()

                            return@setOnClickListener
                        }
                        else{
                            uploadData("By Cheque","", koid, sdpname, tvlocation ,tvmobno ,tvdepdate ,tvamount ,"By Cheque" ,"","" ,tvckeckdate ,tvcheckno ,tvcheckbname ,"")

                        }

                    }
                    else if(paymode.equals("By Internet Banking"))
                    {

                        if (koid.isEmpty() || sdpname.isEmpty()  || tvmobno.isEmpty() || tvdepdate.isEmpty() || tvamount.isEmpty() ||tvibno.isEmpty()) {
                            Snackbar.make(binding.root, "Please fill all fields", Snackbar.LENGTH_SHORT).show()

                            return@setOnClickListener
                        }
                        else
                        {
                            uploadData("By Internet Banking","", koid, sdpname, tvlocation ,tvmobno ,tvdepdate ,tvamount ,"By Internet Banking ","","" ,"" ,"" ,"" ,tvibno)
                        }

                    }
                    else{

                        if (koid.isEmpty() || sdpname.isEmpty() || tvmobno.isEmpty() || tvdepdate.isEmpty() || tvamount.isEmpty()) {
                            Snackbar.make(binding.root, "Please fill all fields", Snackbar.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                        else if(setpaymodetype.equals(""))
                        {
                            Snackbar.make(binding.root, "Select mode of Payment", Snackbar.LENGTH_SHORT).show()

                        }

                    }


                }


            }
            else{
                Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_SHORT).show()

            }

        }
        else{
            binding.btnAddlimitreq.setOnClickListener {

                val koid = binding.txtInputKoid.text.toString()
                val sdpname = binding.txtSdpname.text.toString()
                val tvlocation = binding.txtLocation.text.toString()
                val tvmobno = binding.txtMobileno.text.toString()
                val tvdepdate = binding.txtDddate.text.toString()
                val tvamount = binding.txtAmount.text.toString()
                val paymode =setpaymodetype
                val tvbname = binding.txtBname.text.toString()
                val tvbcode = binding.txtBcode.text.toString()
                val tvckeckdate = binding.txtCheckdate.text.toString()
                val tvcheckno = binding.txtChecknumber.text.toString()
                val tvcheckbname = binding.txtCheckbankname.text.toString()
                val tvibno = binding.txtIbnumber.text.toString()

                if(paymode.equals("By Cash"))
                {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        // For Android 10 and above

                        if (koid.isEmpty() || sdpname.isEmpty() ||tvmobno.isEmpty() || tvdepdate.isEmpty() || tvamount.isEmpty()||tvbname.isEmpty()||tvbcode.isEmpty()) {
                            Snackbar.make(binding.root, "Please fill all fields", Snackbar.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                        else if (selectedImagePath.isNullOrEmpty()) {
                            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }

                        else
                        {
                            uploadData("By Cash" ,selectedImagePath!!, koid, sdpname, tvlocation ,tvmobno ,tvdepdate ,tvamount ,"By Cash" ,tvbname,tvbcode ,"" ,"" ,"" ,"")

                        }
                    } else {

                        if (koid.isEmpty() || sdpname.isEmpty() ||tvmobno.isEmpty() || tvdepdate.isEmpty() || tvamount.isEmpty()||tvbname.isEmpty()||tvbcode.isEmpty()) {
                            Snackbar.make(binding.root, "Please fill all fields", Snackbar.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                        else if (selectedImagePath1.isNullOrEmpty()) {
                            Toast.makeText(this, "Please select an image below 9 ", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }

                        else
                        {
                            uploadData("By Cash" ,selectedImagePath1!!, koid, sdpname, tvlocation ,tvmobno ,tvdepdate ,tvamount ,"By Cash" ,tvbname,tvbcode ,"" ,"" ,"" ,"")

                        }

                        // For Android 9 and below
                    }



                }
                else if(paymode.equals("By Cheque"))
                {

                    if (koid.isEmpty() || sdpname.isEmpty() || tvmobno.isEmpty() || tvdepdate.isEmpty() || tvamount.isEmpty()||tvckeckdate.isEmpty()||tvcheckno.isEmpty()||tvcheckbname.isEmpty()) {
                        Snackbar.make(binding.root, "Please fill all fields", Snackbar.LENGTH_SHORT).show()

                        return@setOnClickListener
                    }
                    else{
                        uploadData("By Cheque","", koid, sdpname, tvlocation ,tvmobno ,tvdepdate ,tvamount ,"By Cheque" ,"","" ,tvckeckdate ,tvcheckno ,tvcheckbname ,"")

                    }

                }
                else if(paymode.equals("By Internet Banking"))
                {

                    if (koid.isEmpty() || sdpname.isEmpty()  || tvmobno.isEmpty() || tvdepdate.isEmpty() || tvamount.isEmpty() ||tvibno.isEmpty()) {
                        Snackbar.make(binding.root, "Please fill all fields", Snackbar.LENGTH_SHORT).show()

                        return@setOnClickListener
                    }
                    else
                    {
                        uploadData("By Internet Banking","", koid, sdpname, tvlocation ,tvmobno ,tvdepdate ,tvamount ,"By Internet Banking ","","" ,"" ,"" ,"" ,tvibno)
                    }

                }
                else{

                    if (koid.isEmpty() || sdpname.isEmpty() || tvmobno.isEmpty() || tvdepdate.isEmpty() || tvamount.isEmpty()) {
                        Snackbar.make(binding.root, "Please fill all fields", Snackbar.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    else if(setpaymodetype.equals(""))
                    {
                        Snackbar.make(binding.root, "Select mode of Payment", Snackbar.LENGTH_SHORT).show()

                    }

                }


            }

        }


    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if(courses[position].equals("By Cash"))
        {
            setpaymodetype="By Cash"
            binding.toplayoutBycash.setVisibility(View.VISIBLE);
            binding.toplayoutCheck.setVisibility(View.GONE);
            binding.toplayoutInternetbanking.setVisibility(View.GONE);

        }
        else if(courses[position].equals("By Cheque"))
        {
            setpaymodetype="By Cheque"

            binding.toplayoutCheck.setVisibility(View.VISIBLE);
            binding.toplayoutBycash.setVisibility(View.GONE);
            binding.toplayoutInternetbanking.setVisibility(View.GONE);


        }
        else if(courses[position].equals("By Internet Banking"))
        {
            setpaymodetype="By Internet Banking"

            binding.toplayoutInternetbanking.setVisibility(View.VISIBLE);
            binding.toplayoutBycash.setVisibility(View.GONE);
            binding.toplayoutCheck.setVisibility(View.GONE)


        }
        else
        {
            binding.toplayoutBycash.setVisibility(View.GONE);
            binding.toplayoutCheck.setVisibility(View.GONE)
            binding.toplayoutInternetbanking.setVisibility(View.GONE);
            setpaymodetype=""

        }

    }

    override   fun onNothingSelected(parent: AdapterView<*>?) {
        // Do nothing if no item is selected
    }



    //ANDROID QABOVE =10
    private fun imageChooser() {
        val i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        launchSomeActivity.launch(i)
    }





    private var launchSomeActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            if (data != null && data.data != null) {
                val selectedImageUri = data.data
                try {
                    selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                        this.contentResolver,
                        selectedImageUri
                    )
                    selectedImagePath = getPathFromUri(selectedImageUri!!)
                    Log.d("AddLimitRequestActivity", "Selected image path: $selectedImagePath")
                    Log.e("AddLimitRequestActivity", "Selected image path: $selectedImagePath")

                    binding.IVPreviewImage.visibility = View.VISIBLE
                    binding.btnUploadimg.visibility = View.GONE
                    binding.IVPreviewImage.setImageBitmap(selectedImageBitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getPathFromUri(uri: Uri): String? {
        var filePath: String? = null
        val cursor = contentResolver.query(uri, arrayOf(MediaStore.Images.Media.DATA), null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                filePath = it.getString(columnIndex)
            }
        }
        return filePath
    }

//androdi below===9
    private fun openGallery() {

    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    pickImageLauncher.launch(intent)
    }

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val imageUri = data?.data
            imageUri?.let { uri ->
                // Handle the image URI (display image in ImageView and send to server)
                setImageToImageView(uri)
                selectedImagePath1 = getPathFromUri1(imageUri!!)

            }
        }
    }

    private fun setImageToImageView(uri: Uri) {
        try {
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            binding.IVPreviewImage.visibility = View.VISIBLE
            binding.IVPreviewImage.setImageBitmap(bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
        }
    }


//    private fun getPathFromUri1(uri: Uri): String? {
//        var filePath: String? = null
//        val cursor = contentResolver.query(uri, arrayOf(MediaStore.Images.Media.DATA), null, null, null)
//        cursor?.use {
//            if (it.moveToFirst()) {
//                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//                filePath = it.getString(columnIndex)
//            }
//        }
//        return filePath
//    }

    private fun getPathFromUri1(uri: Uri): String? {
        var filePath: String? = null

        // Handle content URI (scoped storage)
        if (uri.scheme.equals("content", ignoreCase = true)) {
            val cursor = contentResolver.query(uri, arrayOf(MediaStore.Images.Media.DATA), null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    filePath = it.getString(columnIndex)
                }
            }
        }
        // Handle file URI (direct file path)
        else if (uri.scheme.equals("file", ignoreCase = true)) {
            filePath = uri.path
        }

        return filePath
    }
        override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }


    private fun uploadData(gettype:String ,imagePath: String, koid: String, sdpname: String ,tvlocation: String ,tvmobno:String ,tvdepdate:String,tvamount:String ,paymode:String ,tvbname:String,tvbcode:String ,tvckeckdate:String,tvcheckno:String,tvcheckbname:String ,tvibno:String) {
        if(gettype.equals("By Cash"))
        {
            progressDialog.show()

            val file = File(imagePath)
            if (file.exists()) {
                Log.e("File Exists", "File path is valid")
            } else {
                Log.e("File Error", "File does not exist")
            }
            val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            Log.e("requestFile is imagePart ","imagePart"+requestFile)

            val imagePart = MultipartBody.Part.createFormData("photo1", file.name, requestFile)

            Log.e("imagePart is imagePart ","imagePart"+imagePart)

            val koidbody = koid.toRequestBody("text/plain".toMediaTypeOrNull())
            val sdpnamebody = sdpname.toRequestBody("text/plain".toMediaTypeOrNull())
            val locationbody = tvlocation.toRequestBody("text/plain".toMediaTypeOrNull())
            val mobbody = tvmobno.toRequestBody("text/plain".toMediaTypeOrNull())
            val depdatebody = tvdepdate.toRequestBody("text/plain".toMediaTypeOrNull())
            val amountbody = tvamount.toRequestBody("text/plain".toMediaTypeOrNull())
            val paymodebody = paymode.toRequestBody("text/plain".toMediaTypeOrNull())
            val tvbnamebody = tvbname.toRequestBody("text/plain".toMediaTypeOrNull())
            val tvbcodebody = tvbcode.toRequestBody("text/plain".toMediaTypeOrNull())
            val tvcheckdatebody = tvckeckdate.toRequestBody("text/plain".toMediaTypeOrNull())
            val tvchecknobody = tvcheckno.toRequestBody("text/plain".toMediaTypeOrNull())
            val tvcheckbnamebody = tvcheckbname.toRequestBody("text/plain".toMediaTypeOrNull())
            val tvibnobody = tvibno.toRequestBody("text/plain".toMediaTypeOrNull())

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = RetrofitClient.api.uploadData(imagePart, koidbody, sdpnamebody, locationbody ,mobbody ,depdatebody ,amountbody ,paymodebody ,tvbnamebody ,tvbcodebody ,tvcheckdatebody ,tvcheckbnamebody ,tvchecknobody ,tvibnobody)
                    if (response.isSuccessful) {
                        runOnUiThread {
//                            Toast.makeText(this@AddLimitRequestActivity, "Upload Successful!", Toast.LENGTH_SHORT).show()
                            progressDialog.dismiss()
                            Log.e("which msg","send"+response.message())
                            Log.e("which body","send"+response.body())

                            SweetAlertDialog(this@AddLimitRequestActivity, SweetAlertDialog.SUCCESS_TYPE)
                                .setContentText("Upload Successful!")
                                .setConfirmClickListener {

                                        sDialog -> finish()
                                    sDialog.dismissWithAnimation()
                                }

                                .show()
                        }
                    } else {
                        progressDialog.dismiss()

                        runOnUiThread {
                            Toast.makeText(this@AddLimitRequestActivity, "Upload Failed!", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    progressDialog.dismiss()

                    e.printStackTrace()
                    runOnUiThread {
//                        Toast.makeText(this@AddLimitRequestActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
        else
        {
            progressDialog.show()

            val koidbody = koid.toRequestBody("text/plain".toMediaTypeOrNull())
            val sdpnamebody = sdpname.toRequestBody("text/plain".toMediaTypeOrNull())
            val locationbody = tvlocation.toRequestBody("text/plain".toMediaTypeOrNull())
            val mobbody = tvmobno.toRequestBody("text/plain".toMediaTypeOrNull())
            val depdatebody = tvdepdate.toRequestBody("text/plain".toMediaTypeOrNull())
            val amountbody = tvamount.toRequestBody("text/plain".toMediaTypeOrNull())
            val paymodebody = paymode.toRequestBody("text/plain".toMediaTypeOrNull())
            val tvbnamebody = tvbname.toRequestBody("text/plain".toMediaTypeOrNull())
            val tvbcodebody = tvbcode.toRequestBody("text/plain".toMediaTypeOrNull())
            val tvcheckdatebody = tvckeckdate.toRequestBody("text/plain".toMediaTypeOrNull())
            val tvchecknobody = tvcheckno.toRequestBody("text/plain".toMediaTypeOrNull())
            val tvcheckbnamebody = tvcheckbname.toRequestBody("text/plain".toMediaTypeOrNull())
            val tvibnobody = tvibno.toRequestBody("text/plain".toMediaTypeOrNull())
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = RetrofitClient.api.uploadnoimageData( koidbody, sdpnamebody, locationbody ,mobbody ,depdatebody ,amountbody ,paymodebody ,tvbnamebody ,tvbcodebody ,tvcheckdatebody ,tvcheckbnamebody ,tvchecknobody ,tvibnobody)
                    if (response.isSuccessful) {

                        runOnUiThread {
                            progressDialog.dismiss()
                            Log.e("which msg","send"+response.message())
                            Log.e("which body","send"+response.body())

                            SweetAlertDialog(this@AddLimitRequestActivity, SweetAlertDialog.SUCCESS_TYPE)
                                .setContentText("Upload Successful!")
                                .setConfirmClickListener {

                                        sDialog -> finish()
                                    sDialog.dismissWithAnimation()
                                }

                                .show()
                        }
                    } else {
                        progressDialog.dismiss()

                        runOnUiThread {
                            Toast.makeText(this@AddLimitRequestActivity, "Upload Failed!", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    progressDialog.dismiss()

                    e.printStackTrace()
                    runOnUiThread {
                        Toast.makeText(this@AddLimitRequestActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }


    }


    private fun isInternetConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}