package com.nictko.services.activity

import android.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nictko.services.databinding.ActivityTermsConditionLimitRequestBinding


class TermsConditionLimitRequestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTermsConditionLimitRequestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_terms_condition_limit_request)
        binding = DataBindingUtil.setContentView(this, com.nictko.services.R.layout.activity_terms_condition_limit_request)
//        binding.txtLtinfolimit6.setText(R.string.ltinfolimit6eng+","+R.string.ltinfolimit6eng)
        val ltInfoLimit6 = getString(com.nictko.services.R.string.ltinfolimit6)
        val ltInfoLimit6red = getString(com.nictko.services.R.string.ltinfolimit6red)
        val ltInfoLimit6eng = getString(com.nictko.services.R.string.ltinfolimit6eng)
        val ltInfoLimit6engred = getString(com.nictko.services.R.string.ltinfolimit6engred)
        val mergedStringhi = "$ltInfoLimit6, $ltInfoLimit6red"
        val mergedStringeng = "$ltInfoLimit6eng, $ltInfoLimit6engred"
        binding.txtLtinfolimit6.setText(mergedStringhi)
        binding.txtLtinfolimit6eng.setText(mergedStringeng)

        click()


    }

    private fun click()
    {
        binding.backImg.setOnClickListener {
            finish()
        }


    }
}