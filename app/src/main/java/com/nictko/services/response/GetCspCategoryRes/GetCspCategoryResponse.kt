package com.nictko.services.response

import com.google.gson.annotations.SerializedName


data class GetCspCategoryResponse (

  @SerializedName("csp_category") var cspCategory : String? = null,
  @SerializedName("status") var status      : Int?    = null

)