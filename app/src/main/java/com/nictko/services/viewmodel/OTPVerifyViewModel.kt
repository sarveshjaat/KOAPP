package com.nictko.services.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nictko.services.repository.UserRepository
import com.nictko.services.request.OTPVerifyRequest
import com.nictko.services.request.requestother.OTPVerifyOtherRequest
import com.nictko.services.response.otpresponse.OTPVerifyResponse
import com.nictko.services.responseother.OTPVerifyOtherResponse
import kotlinx.coroutines.launch

class OTPVerifyViewModel (application: Application) : AndroidViewModel(application) {
    val userRepo = UserRepository()
    val otpResult: MutableLiveData<OTPVerifyResponse> = MutableLiveData()
    val otpResult_other: MutableLiveData<OTPVerifyOtherResponse> = MutableLiveData()

    fun otpverifycall(getkoid: String, getotp: String) {
        viewModelScope.launch {
            try {

                val otpVerifyRequest = OTPVerifyRequest(
                    koid = getkoid,
                    otp = getotp

                )
                val response = userRepo.otpUserrepo(otpverifyrequest = otpVerifyRequest)
                if (response?.code() == 200) {
                    Log.e("ty","tyt"+response.body())
                    Log.e("rty","ryr7878"+response.message())
                    otpResult.value=response.body()
//                        loginResult.value = BaseResponse.Success(response.body())
                } else {
//                        loginResult.value = BaseResponse.Error(response?.message())
                    otpResult.value = response!!.body()

                }

            } catch (ex: Exception) {
//                    loginResult.value =ex.message
            }
        }
    }

    fun otpverifycallother(getkoid: String, getotp: String ,getbanktype: String) {
        viewModelScope.launch {
            try {

                val otpVerifyRequest = OTPVerifyOtherRequest(
                    koid = getkoid,
                    otp = getotp,
                    bank = getbanktype
                )
                val response = userRepo.otpUserrepo_otpother(otpverifyrequest = otpVerifyRequest)

                if (response?.code() == 200) {
                    otpResult_other.value=response.body()
//                        loginResult.value = BaseResponse.Success(response.body())
                } else {
//                        loginResult.value = BaseResponse.Error(response?.message())
                    otpResult_other.value = response!!.body()

                }

            } catch (ex: Exception) {
//                    loginResult.value =ex.message
            }
        }
    }

}