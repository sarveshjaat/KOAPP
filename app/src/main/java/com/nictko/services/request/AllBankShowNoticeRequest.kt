package com.nictko.services.request

import com.google.gson.annotations.SerializedName

data class AllBankShowNoticeRequest(

    @SerializedName("bank") var bank  : String? = null
)
