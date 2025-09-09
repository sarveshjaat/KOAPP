package com.nictko.services.request

import com.google.gson.annotations.SerializedName

data class AddComplaintFeedbackRequest(

@SerializedName("id"      ) var id      : Int? = null,
@SerializedName("rating" ) var rating : String?    = null,
@SerializedName("feedback" ) var feedback : String?    = null,


)
