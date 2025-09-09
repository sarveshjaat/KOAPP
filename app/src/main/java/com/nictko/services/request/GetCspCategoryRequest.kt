package com.nictko.services.request

import com.google.gson.annotations.SerializedName

data class GetCspCategoryRequest(
    @SerializedName("koid")
    var koid: String

)
