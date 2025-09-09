package com.nictko.services

import com.google.gson.annotations.SerializedName
import com.nictko.services.response.settlementreport.Data


data class SettlementReportResponse (

  @SerializedName("responseCode"   ) var responseCode   : Int?            = null,
  @SerializedName("responseStatus" ) var responseStatus : String?         = null,
  @SerializedName("messages"       ) var messages       : String?         = null,
  @SerializedName("data"           ) var data           : ArrayList<Data> = arrayListOf(),

)