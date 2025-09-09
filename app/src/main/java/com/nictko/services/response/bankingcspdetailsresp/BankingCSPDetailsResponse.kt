package com.nictko.services.response

import com.google.gson.annotations.SerializedName


data class BankingCSPDetailsResponse (

  @SerializedName("district" ) var district : String? = null,
  @SerializedName("state"    ) var state    : String? = null,
  @SerializedName("status"   ) var status   : Int?    = null

)