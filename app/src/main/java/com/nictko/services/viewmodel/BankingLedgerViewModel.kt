package com.nictko.services.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nictko.services.repository.UserRepository
import com.nictko.services.request.SBINBankingLedgerRequest
import com.nictko.services.request.requestother.BGGBBankingLedgerRequest
import com.nictko.services.request.requestother.BOBBankLedgerRequest
import com.nictko.services.request.requestother.BOIBankingLedgerRequest
import com.nictko.services.request.requestother.BOMBankLedgerRequest
import com.nictko.services.request.requestother.BUPGBankingLedgerRequest
import com.nictko.services.request.requestother.CRGBBankingLedgerRequest
import com.nictko.services.request.requestother.MGBBankingLedgerRequest
import com.nictko.services.request.requestother.MPGBBankingLedger
import com.nictko.services.request.requestother.PNBBankingLedgerRequest
import com.nictko.services.request.requestother.UBINBankLedgerRequest
import com.nictko.services.response.sbiledgerresp.SBIBankingLedgerResponse
import com.nictko.services.responseother.BOBBankingLedgerResponse
import com.nictko.services.responseother.bggbledgerresponse.BGGBBankingLedgerResponsne
import com.nictko.services.responseother.boilledgerresponse.BOIBankingLedgerResponse
import com.nictko.services.responseother.bomledgerresponse.bomledgerresponse.BOMBankingLedgerResponse
import com.nictko.services.responseother.bupgledgerresp.BUPGBankingLedgerResponse
import com.nictko.services.responseother.crgbledgerrespponse.CRGBBankingLedgerResposne
import com.nictko.services.responseother.mgbledgerresponse.MGBBankingLedgerResponse
import com.nictko.services.responseother.mpgbledgerresponse.MPGBBankingLedgerResponse
import com.nictko.services.responseother.pnbledgerresposne.PNBBankingLedgerResponse
import com.nictko.services.responseother.ubinledgerresponse.UBINBankingLedgerResponse
import kotlinx.coroutines.launch

class BankingLedgerViewModel(application: Application) : AndroidViewModel(application) {
    val userRepo = UserRepository()
    val bobResult: MutableLiveData<BOBBankingLedgerResponse> = MutableLiveData()
    val bomResult: MutableLiveData<BOMBankingLedgerResponse> = MutableLiveData()
    val boiResult: MutableLiveData<BOIBankingLedgerResponse> = MutableLiveData()
    val pnbResult: MutableLiveData<PNBBankingLedgerResponse> = MutableLiveData()
    val bupgResult: MutableLiveData<BUPGBankingLedgerResponse> = MutableLiveData()
    val mgbResult: MutableLiveData<MGBBankingLedgerResponse> = MutableLiveData()
    val mpgbResult: MutableLiveData<MPGBBankingLedgerResponse> = MutableLiveData()
    val crgbResult: MutableLiveData<CRGBBankingLedgerResposne> = MutableLiveData()
    val bggbResult: MutableLiveData<BGGBBankingLedgerResponsne> = MutableLiveData()
    val ubinResult: MutableLiveData<UBINBankingLedgerResponse> = MutableLiveData()
    val sbiResult: MutableLiveData<SBIBankingLedgerResponse> = MutableLiveData()

    fun searchbybob(
        getmonth: String,
        getyear: String,
        getapikey: String,
        getbank: String,
        getuserid: String) {

        viewModelScope.launch {
            try {

                val bobledgerrequest = BOBBankLedgerRequest(
                    month = getmonth,
                    year = getyear,
                    api_key = getapikey,
                    bank = getbank,
                    userId = getuserid,
                )
                val response = userRepo.bobledgerrepo(bobrequest = bobledgerrequest)
                if (response?.code() == 200) {
                    bobResult.value = response.body()
                } else {
                    bobResult.value = response!!.body()
                }

            } catch (ex: Exception) {
            }
        }
    }


    fun searchbybom(
        getmonth: String,
        getyear: String,
        getapikey: String,
        getbank: String,
        getuserid: String) {

        viewModelScope.launch {
            try {
                val bomledgerrequest = BOMBankLedgerRequest(
                    month = getmonth,
                    year = getyear,
                    api_key = getapikey,
                    bank = getbank,
                    userId = getuserid,
                )
                val response = userRepo.bomledgerrepo(bomrequest = bomledgerrequest)
                if (response?.code() == 200) {
                    bomResult.value = response.body()
                } else {
                    bomResult.value = response!!.body()

                }

            } catch (ex: Exception) {
            }
        }
    }


    //boi

    fun searchbyboi(
        getmonth: String,
        getyear: String,
        getapikey: String,
        getbank: String,
        getuserid: String) {

        viewModelScope.launch {
            try {

                val boiledgerrequest = BOIBankingLedgerRequest(
                    month = getmonth,
                    year = getyear,
                    api_key = getapikey,
                    bank = getbank,
                    userId = getuserid,
                )
                val response = userRepo.boiledgerrepo(boirequest = boiledgerrequest)
                if (response?.code() == 200) {
                    boiResult.value = response.body()
                } else {
                    boiResult.value = response!!.body()

                }

            } catch (ex: Exception) {
            }
        }
    }


    fun searchbypnb(
        getmonth: String,
        getyear: String,
        getapikey: String,
        getbank: String,
        getuserid: String) {

        viewModelScope.launch {
            try {

                val pnbledgerrequest = PNBBankingLedgerRequest(
                    month = getmonth,
                    year = getyear,
                    api_key = getapikey,
                    bank = getbank,
                    userId = getuserid,
                )
                val response = userRepo.pnbledgerrepo(pnbrequest = pnbledgerrequest)
                if (response?.code() == 200) {
                    pnbResult.value = response.body()
                } else {
                    pnbResult.value = response!!.body()

                }

            } catch (ex: Exception) {
            }
        }
    }

    fun searchbybupg(
        getmonth: String,
        getyear: String,
        getapikey: String,
        getbank: String,
        getuserid: String) {

        viewModelScope.launch {
            try {

                val bupgledgerrequest = BUPGBankingLedgerRequest(
                    month = getmonth,
                    year = getyear,
                    api_key = getapikey,
                    bank = getbank,
                    userId = getuserid,
                )
                val response = userRepo.bupgledgerrepo(bupgrequest = bupgledgerrequest)
                if (response?.code() == 200) {
                    bupgResult.value = response.body()
                } else {
                    bupgResult.value = response!!.body()

                }

            } catch (ex: Exception) {
            }
        }
    }

    fun searchbymgb(
        getmonth: String,
        getyear: String,
        getapikey: String,
        getbank: String,
        getuserid: String) {

        viewModelScope.launch {
            try {

                val mgbledgerrequest = MGBBankingLedgerRequest(
                    month = getmonth,
                    year = getyear,
                    api_key = getapikey,
                    bank = getbank,
                    userId = getuserid,
                )
                val response = userRepo.mgbledgerrepo(mgbgrequest = mgbledgerrequest)
                if (response?.code() == 200) {
                    mgbResult.value = response.body()
                } else {
                    mgbResult.value = response!!.body()

                }

            } catch (ex: Exception) {
            }
        }
    }

    fun searchbympgb(
        getmonth: String,
        getyear: String,
        getapikey: String,
        getbank: String,
        getuserid: String) {

        viewModelScope.launch {
            try {

                val mpgbledgerrequest = MPGBBankingLedger(
                    month = getmonth,
                    year = getyear,
                    api_key = getapikey,
                    bank = getbank,
                    userId = getuserid,
                )
                val response = userRepo.mpgbledgerrepo(mpgbgrequest = mpgbledgerrequest)
                if (response?.code() == 200) {
                    mpgbResult.value = response.body()
                } else {
                    mpgbResult.value = response!!.body()

                }

            } catch (ex: Exception) {
            }
        }
    }

    fun searchbycrgb(
        getmonth: String,
        getyear: String,
        getapikey: String,
        getbank: String,
        getuserid: String) {

        viewModelScope.launch {
            try {

                val crgbledgerrequest = CRGBBankingLedgerRequest(
                    month = getmonth,
                    year = getyear,
                    api_key = getapikey,
                    bank = getbank,
                    userId = getuserid,
                )
                val response = userRepo.crgbledgerrepo(crgbbrequest = crgbledgerrequest)
                if (response?.code() == 200) {
                    crgbResult.value = response.body()
                } else {
                    crgbResult.value = response!!.body()
                }

            } catch (ex: Exception) {
            }
        }
    }

    fun searchbybggb(
        getmonth: String,
        getyear: String,
        getapikey: String,
        getbank: String,
        getuserid: String)
    {
        viewModelScope.launch {
            try {

                val bggbledgerrequest = BGGBBankingLedgerRequest(
                    month = getmonth,
                    year = getyear,
                    api_key = getapikey,
                    bank = getbank,
                    userId = getuserid,
                )
                val response = userRepo.bggbledgerrepo(bggbbbrequest = bggbledgerrequest)
                if (response?.code() == 200) {
                    bggbResult.value = response.body()
                } else {
                    bggbResult.value = response!!.body()

                }

            } catch (ex: Exception) {
            }
        }
    }

    fun searchbyUBIN(
        getmonth: String,
        getyear: String,
        getapikey: String,
        getbank: String,
        getuserid: String)
    {
        viewModelScope.launch {
            try {

                val ubinledgerrequest = UBINBankLedgerRequest(
                    month = getmonth,
                    year = getyear,
                    api_key = getapikey,
                    bank = getbank,
                    userId = getuserid,
                )
                val response = userRepo.ubinledgerrepo(ubinbrequest = ubinledgerrequest)
                if (response?.code() == 200) {
                    ubinResult.value = response.body()
                } else {
                    ubinResult.value = response!!.body()

                }

            } catch (ex: Exception) {
            }
        }
    }

    fun searchbySBI(
        getmonth: String,
        getyear: String,
        getapikey: String,
        getbank: String,
        getuserid: String)
    {
        viewModelScope.launch {
            try {

                val sbiledgerrequest = SBINBankingLedgerRequest(
                    month = getmonth,
                    year = getyear,
                    api_key = getapikey,
                    bank = getbank,
                    userId = getuserid,
                )
                val response = userRepo.sbiledgerrepo(sbibrequest = sbiledgerrequest)

                if (response?.code() == 200) {
                    sbiResult.value = response.body()
                } else {
                    sbiResult.value = response!!.body()

                }

            } catch (ex: Exception) {
            }
        }
    }

}