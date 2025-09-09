package com.nictko.services.activity.activityother

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import com.nictko.services.R
import com.nictko.services.databinding.ActivityDashBordOtherMainBinding
import com.nictko.services.fragment.BankingFragment
import com.nictko.services.fragment.ComplaintOtherFragment
import com.nictko.services.sharedprefrence.SessionManager
import com.nictko.services.viewmodel.VersionDetailsViewModel

class DashBordOtherMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashBordOtherMainBinding
    private val viewModelversion by viewModels<VersionDetailsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dash_bord_other_main)

        val getlogintype = SessionManager.getString(this, "SETTYPELOGIN")

        FirebaseMessaging.getInstance().subscribeToTopic("NoticeChannel").addOnSuccessListener {}

        val BankingFragment= BankingFragment()
        val ComplaintOtherFragment= ComplaintOtherFragment()
        setCurrentFragment(BankingFragment)

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
        click()

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {

                R.id.banking -> {
                    setCurrentFragment(BankingFragment)

                }

                R.id.Complaint_other -> {
                    setCurrentFragment(ComplaintOtherFragment)

                }

            }

            true


        }

    }
    private fun click()
    {
        binding.profileImg.setOnClickListener {

            val intent = Intent(this, ProfileotherActivity::class.java)
            startActivity(intent)


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

            val sad = SweetAlertDialog(this@DashBordOtherMainActivity, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
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

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment_other,fragment)
            commit()
        }

}