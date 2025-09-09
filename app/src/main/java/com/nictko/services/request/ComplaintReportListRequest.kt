package com.nictko.services.request

import com.google.gson.annotations.SerializedName

data class ComplaintReportListRequest(
    @SerializedName("start_date"      ) var start_date      : String? = null,
    @SerializedName("end_date" ) var end_date : String?    = null,
    @SerializedName("mobile" ) var mobile : String?    = null


)
