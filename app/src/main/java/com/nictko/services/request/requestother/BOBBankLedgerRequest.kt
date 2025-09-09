package com.nictko.services.request.requestother

import com.google.gson.annotations.SerializedName

data class BOBBankLedgerRequest(
    @SerializedName("userId")
    var userId: String,
    @SerializedName("bank")
    var bank: String,
    @SerializedName("api_key")
    var api_key: String,
    @SerializedName("month")
    var month: String,
    @SerializedName("year")
    var year: String
)
