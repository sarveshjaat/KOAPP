package com.nictko.services.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nictko.services.repository.UserRepository
import com.nictko.services.request.AddSettlementRequest
import com.nictko.services.response.settlementresponse.AddSettlementResponse
import kotlinx.coroutines.launch

class AddSettlementReqViewModel (application: Application) : AndroidViewModel(application) {
    val userRepo = UserRepository()
    val addsetlemetreqResult: MutableLiveData<AddSettlementResponse> = MutableLiveData()

    fun calladdsettlement(getkoid: String, getmobno: String ,getacctype: String ,getamount: String ,getuserremark:String ,getsettype:String) {

//            loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val addSettlementRequest = AddSettlementRequest(
                    koid = getkoid,
                    mobno = getmobno,
                    accountType = getacctype,
                    amount = getamount,
                    user_nar = getuserremark,
                    st_type = getsettype,

                )
                val response = userRepo.addsettlementrepo(addSettlementRequest = addSettlementRequest)
                if (response?.code() == 200) {
                    addsetlemetreqResult.value=response.body()
                } else {
                    addsetlemetreqResult.value = response!!.body()
                }

            } catch (ex: Exception) {
//                    loginResult.value =ex.message
            }
        }
    }
}