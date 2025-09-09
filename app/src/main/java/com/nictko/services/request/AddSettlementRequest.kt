package com.nictko.services.request

import com.google.gson.annotations.SerializedName

data class AddSettlementRequest(

    @SerializedName("koid")
    var koid: String,
    @SerializedName("mobileno")
    var mobno: String,
    @SerializedName("accountType")
   var accountType: String,
    @SerializedName("amount")
   var amount: String,
    @SerializedName("user_nar")
   var user_nar: String,
    @SerializedName("st_type")
   var st_type: String

)
