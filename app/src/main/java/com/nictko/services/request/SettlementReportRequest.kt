package com.nictko.services.request

import com.google.gson.annotations.SerializedName

data class SettlementReportRequest (
    @SerializedName("start_date")
    var stratdate: String,
    @SerializedName("to_date")
    var todate: String,
    @SerializedName("koid")
    var koid: String,
    @SerializedName("page")
    var page: Int,
    @SerializedName("size")
    var size: Int
)