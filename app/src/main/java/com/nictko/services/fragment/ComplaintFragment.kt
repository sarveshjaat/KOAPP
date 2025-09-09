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
import com.nictko.services.databinding.FragmentComplaintBinding

class ComplaintFragment : Fragment() {
    private lateinit var binding: FragmentComplaintBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_complaint, container, false)

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_complaint, container, false)
        binding.cardOne.setOnClickListener {
            val intent = Intent(requireContext(), AddComplaintRequest::class.java)
            startActivity(intent)
        }

        binding.cardTwo.setOnClickListener {
            val intent = Intent(requireContext(), ComplaintViewListActivity::class.java)
            startActivity(intent)
        }

//        binding.cardThird.setOnClickListener {
//            val intent = Intent(requireContext(), ComplaintFeedbackActivity::class.java)
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
         * @return A new instance of fragment ComplaintFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ComplaintFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}