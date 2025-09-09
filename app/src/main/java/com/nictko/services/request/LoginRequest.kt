package com.nictko.services.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(

    @SerializedName("userId")
    var userid: String,
    @SerializedName("password")
    var password: String,



)
