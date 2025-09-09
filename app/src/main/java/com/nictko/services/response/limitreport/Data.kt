package com.nictko.services.response.limitreport

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("id"             ) var id             : Int?     = null,
  @SerializedName("koid"           ) var koid           : String?  = null,
  @SerializedName("name"           ) var name           : String?  = null,
  @SerializedName("location"       ) var location       : String?  = null,
  @SerializedName("mobno"          ) var mobno          : String?  = null,
  @SerializedName("depDate"        ) var depDate        : String?  = null,
  @SerializedName("amt"            ) var amt            : Int?     = null,
  @SerializedName("photo1"         ) var photo1         : String?  = null,
  @SerializedName("isDuplicate"    ) var isDuplicate    : Boolean? = null,
  @SerializedName("page"           ) var page           : Int?     = null,
  @SerializedName("size"           ) var size           : Int?     = null,
  @SerializedName("start_date"     ) var startDate      : String?  = null,
  @SerializedName("to_date"        ) var toDate         : String?  = null,
  @SerializedName("payMode"        ) var payMode        : String?  = null,
  @SerializedName("brname"         ) var brname         : String?  = null,
  @SerializedName("brcode"         ) var brcode         : String?  = null,
  @SerializedName("chqDate"        ) var chqDate        : String?  = null,
  @SerializedName("chqbank"        ) var chqbank        : String?  = null,
  @SerializedName("chqno"          ) var chqno          : String?  = null,
  @SerializedName("txnno"          ) var txnno          : String?  = null,
  @SerializedName("kstatus"        ) var kstatus        : String?  = null,
  @SerializedName("imgid"          ) var imgid          : String?  = null,
  @SerializedName("entryDatetime"  ) var entryDatetime  : String?  = null,
  @SerializedName("statusDatetime" ) var statusDatetime : String?  = null,
  @SerializedName("reason"         ) var reason         : String?  = null,
  @SerializedName("requestSource"  ) var requestSource  : String?  = null,
  @SerializedName("userId"         ) var userId         : String?  = null,
  @SerializedName("scrNo"          ) var scrNo          : String?  = null,
  @set:JvmName("setDuplicateProperty")

  @SerializedName("duplicate"      ) var duplicate      : Boolean? = null

)