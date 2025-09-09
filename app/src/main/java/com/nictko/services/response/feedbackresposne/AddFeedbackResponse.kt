package com.nictko.services.response.feedbackresposne

import com.google.gson.annotations.SerializedName

data class AddFeedbackResponse(
    @SerializedName("responseCode"   ) var responseCode   : Int?    = null,
    @SerializedName("responseStatus" ) var responseStatus : String? = null,
    @SerializedName("messages"       ) var messages       : String? = null,
    @SerializedName("data"           ) var data           : String? = null
)
