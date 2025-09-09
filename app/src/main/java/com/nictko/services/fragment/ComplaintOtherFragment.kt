package com.nictko.services.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.nictko.services.R
import com.nictko.services.activity.AddComplaintRequest
import com.nictko.services.activity.ComplaintViewListActivity
import com.nictko.services.activity.activityother.AddComplaintOtherActivity
import com.nictko.services.databinding.FragmentBankingBinding
import com.nictko.services.databinding.FragmentComplaintBinding
import com.nictko.services.databinding.FragmentComplaintOtherBinding


class ComplaintOtherFragment : Fragment() {

    private lateinit var binding: FragmentComplaintOtherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_complaint_other, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_complaint_other, container, false)

        binding.cardOne.setOnClickListener {
            val intent = Intent(requireContext(), AddComplaintOtherActivity::class.java)
            startActivity(intent)
        }

        binding.cardTwo.setOnClickListener {
            val intent = Intent(requireContext(), ComplaintViewListActivity::class.java)
            startActivity(intent)
        }

        return binding.root


    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ComplaintOtherFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}