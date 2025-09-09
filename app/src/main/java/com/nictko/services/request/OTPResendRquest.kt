package com.nictko.services.request

import com.google.gson.annotations.SerializedName

data class OTPResendRquest(
    @SerializedName("koId")
    var koid: String,

)
