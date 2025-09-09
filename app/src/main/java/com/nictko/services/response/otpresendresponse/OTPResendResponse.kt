package com.nictko.services.response.otpresendresponse

import com.google.gson.annotations.SerializedName

data class OTPResendResponse(
    @SerializedName("responseCode"   ) var responseCode   : Int?    = null,
    @SerializedName("responseStatus" ) var responseStatus : String? = null,
    @SerializedName("messages"       ) var messages       : String? = null,
    @SerializedName("data"           ) var data           : String? = null

)
