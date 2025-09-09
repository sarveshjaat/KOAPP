package com.nictko.services.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.nictko.services.R
import com.nictko.services.activity.AddLimitRequestActivity
import com.nictko.services.activity.LimitReportViewActivity
import com.nictko.services.databinding.FragmentLimitRequestBinding
import com.nictko.services.sharedprefrence.SessionManager


class LimitRequestFragment : Fragment() {

    private lateinit var binding: FragmentLimitRequestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_limit_request, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_limit_request, container, false)


        val getmobile = SessionManager.getString(requireContext(), "mobile")
        val getkoid = SessionManager.getString(requireContext(), "koid")
        val getusername = SessionManager.getString(requireContext(), "username")
        val getuserrole = SessionManager.getString(requireContext(), "userrole")
        val getsdplocation = SessionManager.getString(requireContext(), "sdplocation")
        val getuserid = SessionManager.getString(requireContext(), "userid")

        binding.cardOne.setOnClickListener {

            val intent = Intent(requireContext(), AddLimitRequestActivity::class.java)
            startActivity(intent)
        }
        binding.cardTwo.setOnClickListener {
            val intent = Intent(requireContext(), LimitReportViewActivity::class.java)
            startActivity(intent)
        }

        return  binding.root

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LimitRequestFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}