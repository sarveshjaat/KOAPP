package com.nictko.services.responseother.otpverifyresp

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("id"          ) var id         : Int?    = null,
  @SerializedName("userId"      ) var userId     : String? = null,
  @SerializedName("password"    ) var password   : String? = null,
  @SerializedName("user_name"   ) var userName   : String? = null,
  @SerializedName("user_status" ) var userStatus : String? = null,
  @SerializedName("userRole"    ) var userRole   : String? = null,
  @SerializedName("csp_code"    ) var cspCode    : String? = null,
  @SerializedName("mobile_no"   ) var mobileNo   : String? = null,
  @SerializedName("apikey"      ) var apikey     : String? = null,
  @SerializedName("bank"        ) var bank       : String? = null

)