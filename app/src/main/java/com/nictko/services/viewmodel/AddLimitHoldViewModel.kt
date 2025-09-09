package com.nictko.services.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nictko.services.repository.UserRepository
import com.nictko.services.request.LimitHoldRequest
import com.nictko.services.response.LimitHoldResponse
import kotlinx.coroutines.launch

class AddLimitHoldViewModel (application: Application) : AndroidViewModel(application){

    val userRepo = UserRepository()

    val addlimiholdrespResult: MutableLiveData<LimitHoldResponse> = MutableLiveData()

    fun calllimitholdadd(getlimitamount: Int ,getkoid:String) {

        viewModelScope.launch {
            try {

                val addlimitholdreq = LimitHoldRequest(
                    limitHold = getlimitamount,
                    koid = getkoid,

                    )
                val response = userRepo.addlimitholdrepo(addlimitholdrepo = addlimitholdreq)
                if (response?.code() == 200) {
                    addlimiholdrespResult.value=response.body()
                } else {
                    addlimiholdrespResult.value = response!!.body()
                }

            } catch (ex: Exception) {
            }
        }
    }


}