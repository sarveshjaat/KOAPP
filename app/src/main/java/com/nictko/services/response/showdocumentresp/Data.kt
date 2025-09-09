package com.nictko.services.response.showdocumentresp

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("id"           ) var id           : Int?    = null,
  @SerializedName("documentName" ) var documentName : String? = null,
  @SerializedName("documentFile" ) var documentFile : String? = null,
  @SerializedName("crntDate"     ) var crntDate     : String? = null,
  @SerializedName("bank"         ) var bank         : String? = null,
  @SerializedName("status"       ) var status       : Int?    = null,
  @SerializedName("photo1"       ) var photo1       : String? = null

)