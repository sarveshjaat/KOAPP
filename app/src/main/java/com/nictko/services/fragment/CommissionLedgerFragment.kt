package com.nictko.services.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.nictko.services.R
import com.nictko.services.activity.NationalBankingLedgerActivity

import com.nictko.services.databinding.FragmentCommissionLedgerBinding

class CommissionLedgerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentCommissionLedgerBinding
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
//        return inflater.inflate(R.layout.fragment_commission_ledger, container, false)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_commission_ledger, container, false)


        binding.cardOne.setOnClickListener { it: View? ->
            val intent = Intent(requireContext(), NationalBankingLedgerActivity::class.java)
            startActivity(intent)
        }
//        binding.cardTwo.setOnClickListener { it: View? ->
//            val intent = Intent(requireContext(), MPCGBankingLedgerActivity::class.java)
//            startActivity(intent)
//        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CommissionLedgerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CommissionLedgerFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}