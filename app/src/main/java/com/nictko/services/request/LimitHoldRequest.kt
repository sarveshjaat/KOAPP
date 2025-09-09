package com.nictko.services.request

import com.google.gson.annotations.SerializedName


data class LimitHoldRequest (

  @SerializedName("koid"      ) var koid      : String? = null,
  @SerializedName("limitHold" ) var limitHold : Int?    = null

)