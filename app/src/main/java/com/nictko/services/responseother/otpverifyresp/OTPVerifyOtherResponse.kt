package com.nictko.services.responseother

import com.google.gson.annotations.SerializedName
import com.nictko.services.responseother.otpverifyresp.Data


data class OTPVerifyOtherResponse (

  @SerializedName("responseCode"   ) var responseCode   : Int?    = null,
  @SerializedName("responseStatus" ) var responseStatus : String? = null,
  @SerializedName("messages"       ) var messages       : String? = null,
  @SerializedName("data"           ) var data           : Data?   = Data()

)