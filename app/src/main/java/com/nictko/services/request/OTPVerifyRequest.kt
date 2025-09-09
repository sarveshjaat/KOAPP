package com.nictko.services.request

import com.google.gson.annotations.SerializedName

data class OTPVerifyRequest(
    @SerializedName("koId")
    var koid: String,
    @SerializedName("otp")
    var otp: String
)
