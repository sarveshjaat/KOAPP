package com.nictko.services.responseother.crgbledgerrespponse

import com.google.gson.annotations.SerializedName


data class CRGBBankingLedgerResposne (

  @SerializedName("responseCode"   ) var responseCode   : Int?            = null,
  @SerializedName("responseStatus" ) var responseStatus : String?         = null,
  @SerializedName("messages"       ) var messages       : String?         = null,
  @SerializedName("data"           ) var data           : ArrayList<Data> = arrayListOf()

)