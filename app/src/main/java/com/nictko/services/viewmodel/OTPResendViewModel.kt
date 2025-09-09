package com.nictko.services.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nictko.services.repository.UserRepository
import com.nictko.services.request.OTPResendRquest
import com.nictko.services.request.requestother.ResendOTPVerifyOtherRequest
import com.nictko.services.response.otpresendresponse.OTPResendResponse
import com.nictko.services.responseother.resendotpother.ResendOTPVerifyOtherResponse
import kotlinx.coroutines.launch

class OTPResendViewModel (application: Application) : AndroidViewModel(application) {
    val userRepo = UserRepository()
    val otpresendResult: MutableLiveData<OTPResendResponse> = MutableLiveData()
    val otpresendResult_other: MutableLiveData<ResendOTPVerifyOtherResponse> = MutableLiveData()

    fun callotpresend(getkoid: String) {
        viewModelScope.launch {
            try {

                val otpresendreq = OTPResendRquest(
                    koid = getkoid,

                )
                val response = userRepo.otpresendUserrepo(otpResendRquest = otpresendreq)
                if (response?.code() == 200) {
                    otpresendResult.value=response.body()
//                        loginResult.value = BaseResponse.Success(response.body())
                } else {
//                        loginResult.value = BaseResponse.Error(response?.message())
                    otpresendResult.value = response!!.body()

                }

            } catch (ex: Exception) {
//                    loginResult.value =ex.message
            }
        }
    }
    fun callotpresend_other(getkoid_other: String ,getbanktype: String) {
        viewModelScope.launch {
            try {

                val otpresendreq = ResendOTPVerifyOtherRequest(
                    koid = getkoid_other,
                    bank = getbanktype,

                    )
                val response = userRepo.otpresendUserrepo_other(otpResendRquest = otpresendreq)
                if (response?.code() == 200) {
                    otpresendResult_other.value=response.body()
//                        loginResult.value = BaseResponse.Success(response.body())
                } else {
//                        loginResult.value = BaseResponse.Error(response?.message())
                    otpresendResult_other.value = response!!.body()

                }

            } catch (ex: Exception) {
//                    loginResult.value =ex.message
            }
        }
    }



}