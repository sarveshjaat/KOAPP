package com.nictko.services.request.requestother

import com.google.gson.annotations.SerializedName

data class ResendOTPVerifyOtherRequest(
    @SerializedName("koId")
    var koid: String,
    @SerializedName("bank")
    var bank: String

)
