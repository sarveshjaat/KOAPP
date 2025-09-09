package com.nictko.services.activity.activityother

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.nictko.services.R
import com.nictko.services.activity.LoginActivity
import com.nictko.services.activity.MainDashBoardActivity
import com.nictko.services.databinding.ActivityProfileBinding
import com.nictko.services.databinding.ActivityProfileotherBinding
import com.nictko.services.sharedprefrence.SessionManager

class ProfileotherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileotherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profileother)

//SessionManager.saveString(this ,"apikey_other", getdata.apikey?: "")
        val getmobile_other = SessionManager.getString(this, "usermobile_other")
        val getuserkoid_other = SessionManager.getString(this, "userId_other")
        val getkoid_cspcodeother = SessionManager.getString(this, "csp_code_other")
        val getkoid_username_other = SessionManager.getString(this, "username_other")

        val getvname = SessionManager.getString(this, "vname")

        binding.txtInputUsermobile.setText(getmobile_other)
        binding.txtInputUsername.setText(getkoid_username_other)
       // binding.txtInputUserrole.setText(getuserrole)
        binding.txtInputUserid.setText(getuserkoid_other)
        binding.txtInputCspcode.setText(getkoid_cspcodeother)


        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        val appVersioncode = packageInfo.versionCode
        val appVersionName= packageInfo.versionName
        binding.txtAppvname.setText("App Version :"+getvname)
        click()

    }
    private fun click()
    {
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

    }

    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent(applicationContext, DashBordOtherMainActivity::class.java)
        startActivity(intent)
    }
}