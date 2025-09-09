package com.nictko.services.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.nictko.services.R
import com.nictko.services.databinding.ActivityComplaintStatusViewBinding
import com.nictko.services.databinding.ActivityComplaintViewListBinding

class ComplaintStatusViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityComplaintStatusViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_complaint_status_view)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_complaint_status_view)

    }
}