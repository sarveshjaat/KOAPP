package com.nictko.services.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nictko.services.R
import com.nictko.services.databinding.ActivityProfileBinding
import com.nictko.services.sharedprefrence.SessionManager

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        val getmobile = SessionManager.getString(this, "mobile")
        val getkoid = SessionManager.getString(this, "koid")
        val getusername = SessionManager.getString(this, "username")
        val getuserrole = SessionManager.getString(this, "userrole")
        val getuserid = SessionManager.getString(this, "userid")
        val getvname = SessionManager.getString(this, "vname")

        binding.txtInputUsermobile.setText(getmobile)
        binding.txtInputUsername.setText(getusername)
        binding.txtInputUserrole.setText(getuserrole)
        binding.txtInputUserid.setText(getuserid)
        binding.txtInputCspcode.setText(getkoid)

        binding.profileImg.setOnClickListener {
            onBackPressed()

        }
        binding.btnLogout.setOnClickListener {
            SessionManager.clearData(this)


            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finishAffinity()

        }
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        val appVersioncode = packageInfo.versionCode
        val appVersionName= packageInfo.versionName
        binding.txtAppvname.setText("App Version :"+getvname)

    }


    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent(applicationContext, MainDashBoardActivity::class.java)
        startActivity(intent)
    }
}