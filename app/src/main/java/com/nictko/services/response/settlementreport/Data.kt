package com.nictko.services.response.settlementreport

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("id"             ) var id           : Int?    = null,
  @SerializedName("ref"            ) var ref          : String? = null,
  @SerializedName("koid"           ) var koid         : String? = null,
  @SerializedName("domain"         ) var domain       : Int?    = null,
  @SerializedName("amount"         ) var amount       : Int?    = null,
  @SerializedName("user_nar"       ) var userNar      : String? = null,
  @SerializedName("edt"            ) var edt          : Long?    = null,
  @SerializedName("statusAcc"      ) var statusAcc    : String? = null,
  @SerializedName("status_acc_tm"  ) var statusAccTm  : Long?    = null,
  @SerializedName("status_acc_nar" ) var statusAccNar : String? = null,
  @SerializedName("mobileno"       ) var mobileno     : String? = null,
  @SerializedName("set_amount"     ) var setAmount    : Int?    = null,
  @SerializedName("st_type"        ) var stType       : String? = null,
  @SerializedName("photo1"         ) var photo1       : String? = null,
  @SerializedName("page"           ) var page         : Int?    = null,
  @SerializedName("size"           ) var size         : Int?    = null,
  @SerializedName("start_date"     ) var startDate    : String? = null,
  @SerializedName("to_date"        ) var toDate       : String? = null

)