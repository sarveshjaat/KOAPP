package com.nictko.services.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nictko.services.repository.UserRepository
import com.nictko.services.request.AllBankShowNoticeRequest
import com.nictko.services.request.requestother.BankingCspDetailsRequest
import com.nictko.services.response.BankingCSPDetailsResponse
import com.nictko.services.response.showdocumentresp.AllBankShowDocumentResponse
import com.nictko.services.response.shownotice.ShowNoticeResponse
import kotlinx.coroutines.launch

class ShowNoticeViewModel (application: Application) : AndroidViewModel(application){
    val userRepo = UserRepository()
    val getshownoticeResult: MutableLiveData<ShowNoticeResponse> = MutableLiveData()
    val getshowdocument_Result: MutableLiveData<AllBankShowDocumentResponse> = MutableLiveData()
    val getcspdetails_Result: MutableLiveData<BankingCSPDetailsResponse> = MutableLiveData()

    fun getcallviewmodelshownotice(getbanktype:String) {
        Log.e("call","fdf"+getbanktype)

        viewModelScope.launch {
            try {
                val allbanknotice = AllBankShowNoticeRequest(
                    bank = getbanktype,
                )

                val response = userRepo.allbankshownotice_repo(allbankshownoticereq = allbanknotice)
                if (response?.code() == 200) {
                    getshownoticeResult.value=response.body()

                } else {

                    getshownoticeResult.value = response!!.body()

                }

            } catch (ex: Exception) {
            }
        }



    }

    fun getcallviewmodel_showdocument(getbanktype:String) {
        Log.e("call","fdf"+getbanktype)

        viewModelScope.launch {
            try {
                val allbanknotice = AllBankShowNoticeRequest(
                    bank = getbanktype,
                )

                val response = userRepo.allbankshowdocument_repo(allbankshownoticereq = allbanknotice)
                if (response?.code() == 200) {
                    getshowdocument_Result.value=response.body()

                } else {

                    getshowdocument_Result.value = response!!.body()

                }

            } catch (ex: Exception) {
            }
        }



    }

    fun getcallviewmodel_cspdetails(getuserkoid_other:String ,getbanktype:String) {
        Log.e("call","fdf"+getbanktype)

        viewModelScope.launch {
            try {
                val cspbankingdetils = BankingCspDetailsRequest(
                    bank = getbanktype,
                    koid = getuserkoid_other,
                )

                val response = userRepo.cspbankingdeatils_repo(cspbankingdtlreq = cspbankingdetils)
                if (response?.code() == 200) {
                    getcspdetails_Result.value=response.body()

                } else {

                    getcspdetails_Result.value = response!!.body()

                }

            } catch (ex: Exception) {
            }
        }



    }



}