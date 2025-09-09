package com.nictko.services.request.requestother

import com.google.gson.annotations.SerializedName

data class BankingCspDetailsRequest(
    @SerializedName("bank")
    var bank: String,
    @SerializedName("koid")
    var koid: String

)
