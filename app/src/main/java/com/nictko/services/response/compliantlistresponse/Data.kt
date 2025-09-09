package com.nictko.services.response.compliantlistresponse

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("id"          ) var id          : Int?    = null,
  @SerializedName("state"       ) var state       : String? = null,
  @SerializedName("division"    ) var division    : String? = null,
  @SerializedName("dist"        ) var dist        : String? = null,
  @SerializedName("name"        ) var name        : String? = null,
  @SerializedName("mobile"      ) var mobile      : String? = null,
  @SerializedName("serviceName" ) var serviceName : String? = null,
  @SerializedName("serviceId"   ) var serviceId   : String? = null,
  @SerializedName("msg"         ) var msg         : String? = null,
  @SerializedName("bankName"    ) var bankName    : String? = null,
  @SerializedName("compCat"     ) var compCat     : String? = null,
  @SerializedName("alt_mobile"  ) var altMobile   : String? = null,
  @SerializedName("rating"      ) var rating      : String? = null,
  @SerializedName("feedback"    ) var feedback    : String? = null,
  @SerializedName("feedback_dt" ) var feedbackDt  : String? = null,
  @SerializedName("cspCat"      ) var cspCat      : String? = null,
  @SerializedName("log_time"      ) var log_time      : String? = null,
  @SerializedName("resolution_time" ) var resolution_time      : String? = null,
  @SerializedName("call_status") var call_status      : String? = null,
  @SerializedName("response_text" ) var response_text      : String? = null,

)