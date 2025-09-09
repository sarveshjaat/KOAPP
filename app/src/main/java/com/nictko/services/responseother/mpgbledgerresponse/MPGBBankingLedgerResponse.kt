package com.nictko.services.responseother.mpgbledgerresponse

import com.google.gson.annotations.SerializedName


data class MPGBBankingLedgerResponse (

  @SerializedName("responseCode"   ) var responseCode   : Int?            = null,
  @SerializedName("responseStatus" ) var responseStatus : String?         = null,
  @SerializedName("messages"       ) var messages       : String?         = null,
  @SerializedName("data"           ) var data           : ArrayList<Data> = arrayListOf()

)