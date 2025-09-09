package com.nictko.services

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.nictko.services.activity.LoginActivity
import com.nictko.services.activity.MainDashBoardActivity
import com.nictko.services.sharedprefrence.SessionManager
import android.Manifest
import com.nictko.services.activity.activityother.DashBordOtherMainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        val token = SessionManager.getToken(this)
        val getkoid = SessionManager.getString(this, "koid")
        val getbanktype = SessionManager.getString(this, "setbanktype")
        val getkoidother = SessionManager.getString(this, "koidother")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermission()
        } else {
            launchMainActivity()
        }

    }

    private fun requestNotificationPermission() {
        val permission = Manifest.permission.POST_NOTIFICATIONS

        // Check if permission is already granted
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            launchMainActivity()
        } else {
            // Request permission
            requestPermissionLauncher.launch(permission)
        }
    }
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            launchMainActivity() // Continue regardless of result
        }
//
    private fun launchMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed({


            if(SessionManager.getString(this, "setbanktype").equals("SBI"))
            {
                if(SessionManager.getString(this, "koid")!=null)
                {
                    startActivity(Intent(this, MainDashBoardActivity::class.java))
                    finish()
                }else
                {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
            else
            {
                if(SessionManager.getString(this, "koidother")!=null)
                {
                    startActivity(Intent(this, DashBordOtherMainActivity::class.java))
                    finish()
                }else
                {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }

            // Your Code
        }, 3000)


    }

    }