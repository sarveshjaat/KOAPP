package com.nictko.services.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nictko.services.SettlementReportResponse
import com.nictko.services.repository.UserRepository
import com.nictko.services.request.SettlementReportRequest
import kotlinx.coroutines.launch

class SettlementReportViewModel (application: Application) : AndroidViewModel(application){

    val userRepo = UserRepository()
    val setlemetreportResult: MutableLiveData<SettlementReportResponse> = MutableLiveData()

    fun callsettlementreport(getstartdate: String, gettodate: String , getkoid: String,getpage: Int ,getsize: Int ) {

//            loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val settlementReportRequest = SettlementReportRequest(
                    stratdate = getstartdate,
                    todate = gettodate,
                    koid = getkoid,
                    page = getpage,
                    size = getsize
                    )
                val response = userRepo.settlmentreportrepo(ViewSettlementReportReq = settlementReportRequest)
                if (response?.code() == 200) {
//                    setlemetreportResult.value=response.body()
                    setlemetreportResult.value=response.body()
//                    addsetlemetreqResult.value=response.body()

                } else {
                    setlemetreportResult.value = response!!.body()
//                    setlemetreportResult.value=response.body()

                }

            } catch (ex: Exception) {
//                    loginResult.value =ex.message
                Log.e("API_ERROR", "Exception occurred: ${ex.message}")

            }
        }
    }
}