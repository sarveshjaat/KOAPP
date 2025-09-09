package com.nictko.services.response

import com.google.gson.annotations.SerializedName


data class AddComplaintResponse (

  @SerializedName("responseCode"   ) var responseCode   : Int?    = null,
  @SerializedName("responseStatus" ) var responseStatus : String? = null,
  @SerializedName("messages"       ) var messages       : String? = null,
  @SerializedName("data"           ) var data           : String? = null

)