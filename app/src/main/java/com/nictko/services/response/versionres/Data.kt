package com.nictko.services.response.versionres

import com.google.gson.annotations.SerializedName

data class Data(

    @SerializedName("version_code"               ) var version_code               : Int?    = null,
    @SerializedName("version_name"           ) var version_name           : String? = null,


    )
