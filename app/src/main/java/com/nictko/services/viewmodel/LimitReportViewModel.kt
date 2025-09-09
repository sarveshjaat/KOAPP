package com.nictko.services.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nictko.services.LimitReportResponse
import com.nictko.services.repository.UserRepository
import com.nictko.services.request.LimitReportRequest
import kotlinx.coroutines.launch

class LimitReportViewModel (application: Application) : AndroidViewModel(application){

    val userRepo = UserRepository()
    val limitreportResult: MutableLiveData<LimitReportResponse> = MutableLiveData()

    fun calllimitreport(getstartdate: String, gettodate: String , getkoid: String,getpage: Int ,getsize: Int ) {

//            loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val limitReportRequest = LimitReportRequest(
                    stratdate = getstartdate,
                    todate = gettodate,
                    koid = getkoid,
                    page = getpage,
                    size = getsize
                )
                val response = userRepo.limitreportrepo(ViewlimitReportReq = limitReportRequest)
                if (response?.code() == 200) {
                    limitreportResult.value=response.body()
                } else {
                    limitreportResult.value = response!!.body()
                }

            } catch (ex: Exception) {
//                    loginResult.value =ex.message
                Log.e("API_ERROR", "Exception occurred: ${ex.message}")


            }
        }
    }


}