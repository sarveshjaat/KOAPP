package com.nictko.services.request.requestother

import com.google.gson.annotations.SerializedName

data class OTPVerifyOtherRequest(

    @SerializedName("koId")
    var koid: String,
    @SerializedName("otp")
    var otp: String,
    @SerializedName("bank")
    var bank: String

)
