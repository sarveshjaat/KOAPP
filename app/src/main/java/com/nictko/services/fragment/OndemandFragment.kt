package com.nictko.services.fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.snackbar.Snackbar
import com.nictko.services.R
import com.nictko.services.activity.AddSettlementRequestActivity
import com.nictko.services.activity.LimitholdActivity
import com.nictko.services.activity.SettlementReportViewActivity
import com.nictko.services.adapter.LimitReportAdapter
import com.nictko.services.adapter.ShowDocumentAdapter
import com.nictko.services.adapter.ShowNoticeAdapter
import com.nictko.services.databinding.FragmentOndemandBinding
import com.nictko.services.sharedprefrence.SessionManager
import com.nictko.services.viewmodel.ShowNoticeViewModel
import com.nictko.services.viewmodel.VersionDetailsViewModel


class OndemandFragment : Fragment() {
    private lateinit var binding: FragmentOndemandBinding
    private lateinit var progressDialog: Dialog
    private val viewModelshownotice by viewModels<ShowNoticeViewModel>()
    private lateinit var adapter: ShowNoticeAdapter
    private lateinit var adapter_document: ShowDocumentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ondemand, container, false)
        val getmobile = SessionManager.getString(requireContext(), "mobile")
        val getkoid = SessionManager.getString(requireContext(), "koid")
        val getusername = SessionManager.getString(requireContext(), "username")
        val getuserrole = SessionManager.getString(requireContext(), "userrole")
        val getuserid = SessionManager.getString(requireContext(), "userid")
        val getbanktype = SessionManager.getString(requireContext(), "setbanktype")

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
            val intent = Intent(requireContext(), AddSettlementRequestActivity::class.java)
            startActivity(intent)
        }
        binding.cardTwo.setOnClickListener {
            val intent = Intent(requireContext(), SettlementReportViewActivity::class.java)
            startActivity(intent)
        }
        binding.cardThird.setOnClickListener {
            val intent = Intent(requireContext(), LimitholdActivity::class.java)
            startActivity(intent)
        }

        viewModelshownotice.getcallviewmodelshownotice(getbanktype = getbanktype.toString())
        viewModelshownotice.getcallviewmodel_showdocument(getbanktype = getbanktype.toString())

        observeData()
        observeData_document()


        return binding.root
    }

    private fun observeData() {
        progressDialog.show()
        viewModelshownotice.getshownoticeResult.observe(viewLifecycleOwner) {

            Log.e("sfdfd", "fdf")
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

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OndemandFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onResume() {
        super.onResume()
        val getbanktype = SessionManager.getString(requireContext(), "setbanktype")
        viewModelshownotice.getcallviewmodelshownotice(getbanktype = getbanktype.toString())
        viewModelshownotice.getcallviewmodel_showdocument(getbanktype = getbanktype.toString())
    }


}