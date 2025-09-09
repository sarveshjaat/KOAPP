package com.nictko.services.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nictko.services.repository.UserRepository
import com.nictko.services.request.LoginRequest
import com.nictko.services.response.VersionResponse
import kotlinx.coroutines.launch

class VersionDetailsViewModel (application: Application) : AndroidViewModel(application) {
    val userRepo = UserRepository()
    val getversionResult: MutableLiveData<VersionResponse> = MutableLiveData()

    fun getcallviewmodelversion() {

//            loginResult.value = BaseResponse.Loading()

        viewModelScope.launch {
            try {
                val response = userRepo.versionrepo()
                if (response?.code() == 200) {
                    getversionResult.value=response.body()
//                        loginResult.value = BaseResponse.Success(response.body())
                } else {
//                        loginResult.value = BaseResponse.Error(response?.message())
                    getversionResult.value = response!!.body()

                }

            } catch (ex: Exception) {
//                    loginResult.value =ex.message
            }
        }
    }

}