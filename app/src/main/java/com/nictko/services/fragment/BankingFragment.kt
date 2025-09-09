package com.nictko.services.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nictko.services.R
import com.nictko.services.activity.NationalBankingLedgerActivity
import com.nictko.services.activity.activityother.BGGBBankingLedgerActivity
import com.nictko.services.activity.activityother.BOBBankingLedgerActivity
import com.nictko.services.activity.activityother.BOIBankingLedgerActivity
import com.nictko.services.activity.activityother.BOMBankingLedgerActivity
import com.nictko.services.activity.activityother.BUPGBankingLedgerActivity
import com.nictko.services.activity.activityother.CRGBBankingActivity
import com.nictko.services.activity.activityother.MGBBankingLedgerActivity
import com.nictko.services.activity.activityother.MPGBBankingActivity
import com.nictko.services.activity.activityother.PNBBankingLedgerActivity
import com.nictko.services.activity.activityother.UBIBankingLedgerActivity
import com.nictko.services.adapter.ShowDocumentAdapter
import com.nictko.services.adapter.ShowNoticeAdapter
import com.nictko.services.databinding.FragmentBankingBinding
import com.nictko.services.sharedprefrence.SessionManager
import com.nictko.services.viewmodel.ShowNoticeViewModel


class BankingFragment : Fragment() {
    private val viewModelshownotice by viewModels<ShowNoticeViewModel>()
    private lateinit var binding: FragmentBankingBinding
    private lateinit var progressDialog: Dialog
    private lateinit var adapter: ShowNoticeAdapter
    private lateinit var adapter_document: ShowDocumentAdapter

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
        //return inflater.inflate(R.layout.fragment_banking, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_banking, container, false)
        val setbanktype_other = SessionManager.getString(requireContext(), "setbanktype")
        val setuserkoid_other = SessionManager.getString(requireContext(), "userId_other")
        val getallbankdistrict = SessionManager.getString(requireContext(), "allbankother_district")
        val getallbankstate = SessionManager.getString(requireContext(), "allbankother_state")

        progressDialog = Dialog(requireContext())
        val view = layoutInflater.inflate(R.layout.progress_dialog, null)
        progressDialog.setContentView(view)
        progressDialog.setCancelable(false)

        binding.recyclerviewShownotice.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewShowdocument.layoutManager = LinearLayoutManager(context)

        // Initialize Adapter
        adapter = ShowNoticeAdapter(emptyList())
        adapter_document = ShowDocumentAdapter(emptyList())
        binding.recyclerviewShownotice.adapter = adapter
        binding.recyclerviewShowdocument.adapter = adapter_document

        binding.cardOne.setOnClickListener {

            if (setbanktype_other.equals("BOB")) {
                val intent = Intent(requireContext(), BOBBankingLedgerActivity::class.java)
                startActivity(intent)

            } else if (setbanktype_other.equals("BOI")) {
                val intent = Intent(requireContext(), BOIBankingLedgerActivity::class.java)
                startActivity(intent)
            } else if (setbanktype_other.equals("PNB")) {
                val intent = Intent(requireContext(), PNBBankingLedgerActivity::class.java)
                startActivity(intent)
            } else if (setbanktype_other.equals("BOM")) {
                val intent = Intent(requireContext(), BOMBankingLedgerActivity::class.java)
                startActivity(intent)
            }
            // changes need live
            else if (setbanktype_other.equals("BUPGB")) {
                val intent = Intent(requireContext(), BUPGBankingLedgerActivity::class.java)
                startActivity(intent)
            } else if (setbanktype_other.equals("UBIN")) {
                val intent = Intent(requireContext(), UBIBankingLedgerActivity::class.java)
                startActivity(intent)
            } else if (setbanktype_other.equals("MGB")) {

                val intent = Intent(requireContext(), MGBBankingLedgerActivity::class.java)
                startActivity(intent)
            } else if (setbanktype_other.equals("BGGB")) {
                val intent = Intent(requireContext(), BGGBBankingLedgerActivity::class.java)
                startActivity(intent)
            } else if (setbanktype_other.equals("CRGB")) {
                val intent = Intent(requireContext(), CRGBBankingActivity::class.java)
                startActivity(intent)
            } else if (setbanktype_other.equals("MPGB")) {
                val intent = Intent(requireContext(), MPGBBankingActivity::class.java)
                startActivity(intent)
            }


        }
        viewModelshownotice.getcallviewmodelshownotice(getbanktype = setbanktype_other.toString())
        viewModelshownotice.getcallviewmodel_showdocument(getbanktype = setbanktype_other.toString())
        viewModelshownotice.getcallviewmodel_cspdetails(getuserkoid_other= setuserkoid_other.toString(),getbanktype = setbanktype_other.toString())

        observeData()
        observeData_document()

        if (!getallbankstate.isNullOrEmpty() && !getallbankdistrict.isNullOrEmpty()) {
            // ✅ Both exist → use cached values
            Log.e("alredy","bhfh")

            // Example: show values or set to UI


        } else {
            observeData_getcspdetails()

        }


        return binding.root

    }

    private fun observeData() {

        progressDialog.show()
        viewModelshownotice.getshownoticeResult.observe(viewLifecycleOwner) {
            if (it.messages.equals("No data found")) {
                progressDialog.dismiss()
                binding.cardNotice.setVisibility(View.GONE);
                binding.cardNoticeannoucement.setVisibility(View.GONE);

            } else if (it.messages == null) {
                progressDialog.dismiss()
                Log.e("sammm show notice nulll", "ty...." + it.data)
                binding.cardNotice.setVisibility(View.VISIBLE);
                binding.cardNoticeannoucement.setVisibility(View.VISIBLE);
                it.data?.let { adapter.updateData(it) }
            } else {
                progressDialog.dismiss()
                Log.e("sammm show notice", "ty...." + it.data)
                binding.cardNotice.setVisibility(View.VISIBLE);
                binding.cardNoticeannoucement.setVisibility(View.VISIBLE);
                it.data?.let { adapter.updateData(it) }
            }

            // your code here
        }
    }

    private fun observeData_document() {
        progressDialog.show()
        viewModelshownotice.getshowdocument_Result.observe(viewLifecycleOwner) {

            Log.e("sfdfd", "fdf")
            if (it.messages.equals("No data found")) {
                progressDialog.dismiss()

                binding.cardDocument.setVisibility(View.GONE);
                binding.cardRviewDocument.setVisibility(View.GONE);

            } else if (it.messages == null) {
                progressDialog.dismiss()

//                Log.e("sammm show notice nulll","ty...."+it.data)
                binding.cardDocument.setVisibility(View.VISIBLE);
                binding.cardRviewDocument.setVisibility(View.VISIBLE);
                it.data?.let { adapter_document.updateData(it) }


            } else if (it.messages.equals("Data fetch error")) {

                progressDialog.dismiss()

                binding.cardDocument.setVisibility(View.GONE);
                binding.cardRviewDocument.setVisibility(View.GONE);
            } else {
                progressDialog.dismiss()

//                Log.e("sammm show notice","ty...."+it.data)
                binding.cardDocument.setVisibility(View.VISIBLE);
                binding.cardRviewDocument.setVisibility(View.VISIBLE);
                it.data?.let { adapter_document.updateData(it) }


            }

            // your code here
        }
    }
    private fun observeData_getcspdetails() {
        progressDialog.show()

        viewModelshownotice.getcspdetails_Result.observe(viewLifecycleOwner) {
            if (it.status==200) {

                progressDialog.dismiss()
                Log.e("it.district","bhfh"+it.district)
                SessionManager.saveString(requireContext(), "allbankother_district", it.district ?: "")
                SessionManager.saveString(requireContext(), "allbankother_state", it.state ?: "")


            }

            else {
                progressDialog.dismiss()

//                binding.cardNotice.setVisibility(View.VISIBLE);
//                binding.cardNoticeannoucement.setVisibility(View.VISIBLE);
//                it.data?.let { adapter.updateData(it) }
            }

            // your code here
        }



    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BankingFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onResume() {
        super.onResume()
        val setbanktype_other = SessionManager.getString(requireContext(), "setbanktype")
        viewModelshownotice.getcallviewmodelshownotice(getbanktype = setbanktype_other.toString())
        viewModelshownotice.getcallviewmodel_showdocument(getbanktype = setbanktype_other.toString())
    }



}