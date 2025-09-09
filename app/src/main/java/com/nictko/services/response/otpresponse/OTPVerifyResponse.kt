package com.nictko.services.response.otpresponse

import com.google.gson.annotations.SerializedName

data class OTPVerifyResponse (

  @SerializedName("responseCode"   ) var responseCode   : Int?    = null,
  @SerializedName("responseStatus" ) var responseStatus : String? = null,
  @SerializedName("messages"       ) var messages       : String? = null,
  @SerializedName("data"           ) var data           : Data?   = Data()

)