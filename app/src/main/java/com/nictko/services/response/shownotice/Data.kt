package com.nictko.services.response.shownotice

import com.google.gson.annotations.SerializedName


data class Data(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("notice_desc") var noticeDesc: String? = null,
    @SerializedName("crntDate") var crntDate: String? = null,
    @SerializedName("status") var status: Int? = null,
    @SerializedName("bank") var bank: String? = null

)