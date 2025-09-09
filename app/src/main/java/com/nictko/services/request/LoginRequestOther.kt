package com.nictko.services.request

import com.google.gson.annotations.SerializedName

data class LoginRequestOther(


    @SerializedName("userId")
    var userid: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("bank")
    var bank: String,


)
