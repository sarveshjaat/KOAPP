package com.nictko.services.response

import com.google.gson.annotations.SerializedName
import com.nictko.services.response.versionres.Data


data class VersionResponse (

  @SerializedName("responseCode"   ) var responseCode   : Int?    = null,
  @SerializedName("responseStatus" ) var responseStatus : String? = null,
  @SerializedName("messages"       ) var messages       : String? = null,
  @SerializedName("data"           ) var data           : Data?   = Data()

)