package com.nictko.services.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nictko.services.request.LoginRequest
import com.nictko.services.repository.UserRepository
import com.nictko.services.request.LoginRequestOther
import com.nictko.services.response.LoginResponse
import com.nictko.services.response.loginresponseother.LoginResponseOther
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

        val userRepo = UserRepository()
        val loginResult: MutableLiveData<LoginResponse> = MutableLiveData()
        val loginResultother: MutableLiveData<LoginResponseOther> = MutableLiveData()


        fun loginUser(getuserid: String, getpwd: String) {

//            loginResult.value = BaseResponse.Loading()
            viewModelScope.launch {
                try {

                    val loginRequest = LoginRequest(
                        userid = getuserid,
                        password = getpwd

                    )
                    val response = userRepo.loginUserrepo(loginRequest = loginRequest)
                    if (response?.code() == 200) {
                        loginResult.value=response.body()
//                        loginResult.value = BaseResponse.Success(response.body())
                    } else {
//                        loginResult.value = BaseResponse.Error(response?.message())
                        loginResult.value = response!!.body()

                    }

                } catch (ex: Exception) {
//                    loginResult.value =ex.message
                }
            }
        }

    fun loginUserother(getuserid: String, getpwd: String ,getbanktype:String) {

        viewModelScope.launch {
            try {

                val loginRequestother = LoginRequestOther(
                    userid = getuserid,
                    password = getpwd,
                    bank=getbanktype

                )
                val response = userRepo.loginUserotherrepo(loginRequestother = loginRequestother)
                if (response?.code() == 200) {
                    loginResultother.value=response.body()
//                        loginResult.value = BaseResponse.Success(response.body())
                } else {
//                        loginResult.value = BaseResponse.Error(response?.message())
                    loginResultother.value = response!!.body()

                }

            } catch (ex: Exception) {
//                    loginResult.value =ex.message
            }
        }
    }


}
