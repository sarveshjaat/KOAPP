package com.nictko.services.responseother.bomledgerresponse.bomledgerresponse

import com.google.gson.annotations.SerializedName
import com.nictko.services.responseother.bomledgerresponse.Data


data class BOMBankingLedgerResponse (

  @SerializedName("responseCode"   ) var responseCode   : Int?            = null,
  @SerializedName("responseStatus" ) var responseStatus : String?         = null,
  @SerializedName("messages"       ) var messages       : String?         = null,
  @SerializedName("data"           ) var data           : ArrayList<Data> = arrayListOf()

)