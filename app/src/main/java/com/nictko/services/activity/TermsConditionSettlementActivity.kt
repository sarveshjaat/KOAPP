package com.nictko.services.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nictko.services.R
import com.nictko.services.databinding.ActivityTermsConditionSettlementBinding

class TermsConditionSettlementActivity : AppCompatActivity() {


    private lateinit var binding: ActivityTermsConditionSettlementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_terms_condition_settlement)
        click()
    }
    private fun click()
    {
        binding.backImg.setOnClickListener {
            finish()
        }
    }
}