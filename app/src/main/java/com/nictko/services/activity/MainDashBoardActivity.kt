package com.nictko.services.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import com.nictko.services.R
import com.nictko.services.databinding.ActivityMainDashBoardBinding
import com.nictko.services.fragment.CommissionLedgerFragment
import com.nictko.services.fragment.ComplaintFragment
import com.nictko.services.fragment.LimitRequestFragment
import com.nictko.services.fragment.OndemandFragment
import com.nictko.services.sharedprefrence.SessionManager
import com.nictko.services.viewmodel.VersionDetailsViewModel

class MainDashBoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainDashBoardBinding
    private val viewModelversion by viewModels<VersionDetailsViewModel>()

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_dash_board)
        val mobile = SessionManager.getString(this, "mobile")
        val koid = SessionManager.getString(this, "koid")
        val getlogintype = SessionManager.getString(this, "SETTYPELOGIN")

        Log.e("getlogintype",".."+getlogintype)

        FirebaseMessaging.getInstance().subscribeToTopic("NoticeChannel").addOnSuccessListener {

        }
        if( SessionManager.getString(this, "SETTYPELOGIN").equals("1"))
        {

        }
        else{
            SessionManager.clearData(this)
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finishAffinity()
        }

        if (mobile != null && koid != null) {
            // Use the retrieved values as needed
            Log.d("MainDashBoard", "Mobile: $mobile, KOID: $koid")
        } else {
            Log.e("MainDashBoard", "Mobile or KOID not found")
        }

        click()
        viewModelversion.getcallviewmodelversion()
        viewModelversion.getversionResult.observe(this) {
//            progressDialog.dismiss()
            if(it.responseStatus.equals("success"))
            {
                val versionName = it.data?.version_name
                val versionCode = it.data?.version_code
                Log.e("VersionName", "Version name: $versionName")
                Log.e("VersionCode", "Version code: $versionCode")
                setversiondtl(versionName ,versionCode)
            }
            else{
                processError(it.messages)
            }
        }
        val CommissionFragment=CommissionLedgerFragment()
        val ComplaintFragment=ComplaintFragment()
        val LimitRequestFragment=LimitRequestFragment()
        val OndemandFragment=OndemandFragment()
        setCurrentFragment(OndemandFragment)
        binding.termsClick.setOnClickListener {
            val intent = Intent(this, TermsConditionSettlementActivity::class.java)
            startActivity(intent)
        }
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
//                R.id.ondemand->setCurrentFragment(OndemandFragment)
//                R.id.limit->setCurrentFragment(LimitRequestFragment)
//                R.id.Commission->setCurrentFragment(CommissionFragment)
//                R.id.complaint->setCurrentFragment(ComplaintFragment)
                R.id.ondemand -> {
                    setCurrentFragment(OndemandFragment)
                    binding.termsClick.visibility = View.VISIBLE
                    binding.termsClick.setOnClickListener {
                        val intent = Intent(this, TermsConditionSettlementActivity::class.java)
                        startActivity(intent)
                    }

                }

                R.id.limit -> {
                    setCurrentFragment(LimitRequestFragment)
                    binding.termsClick.visibility = View.VISIBLE
                    binding.termsClick.setOnClickListener {
                        val intent = Intent(this, TermsConditionLimitRequestActivity::class.java)
                        startActivity(intent)

                    }

                }

                R.id.Commission -> {
                    setCurrentFragment(CommissionFragment)
                    binding.termsClick.visibility = View.GONE
                }

                R.id.complaint -> {
                    setCurrentFragment(ComplaintFragment)
                    binding.termsClick.visibility = View.GONE

                }

            }

            true


    }

}

    fun setversiondtl(getvname: String? ,getvcode: Int?) {

        SessionManager.saveString(this ,"vname", getvname.toString())
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        val appVersioncode = packageInfo.versionCode
        val appVersionName= packageInfo.versionName
        Log.e("appVersionName","ty...."+appVersionName)
        val finalversioncoderesp=getvcode.toString()
        val finalversioncodepackage=appVersioncode.toString()

        if (finalversioncodepackage == finalversioncoderesp) {
//            finalversioncoderesp?.let { Snackbar.make(binding.root, finalversioncoderesp, Snackbar.LENGTH_SHORT).show() }
        }
        else
        {


                val sad = SweetAlertDialog(this@MainDashBoardActivity, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                sad.setCustomImage(R.drawable.updatednew)
                sad.setTitleText(getString(R.string.update_available))
                sad.setContentText(getString(R.string.new_update_is_available_on_google_play_store))
                sad.setConfirmText(getString(R.string.update))
                sad.setCancelable(false)
                sad.setConfirmClickListener {
                    sad.dismissWithAnimation()
                    val playStoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+"com.nictko.services"))
                    playStoreIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    this.startActivity(playStoreIntent)
                    finish()
                }.show()





        }

    }

    fun processError(msg: String?) {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        msg?.let { Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show() }

    }
    private fun click()
    {
        binding.profileImg.setOnClickListener {

            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)


        }
    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

}