package com.nictko.services.response.otpresponse

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("id"               ) var id               : Int?    = null,
  @SerializedName("stcode"           ) var stcode           : String? = null,
  @SerializedName("stname"           ) var stname           : String? = null,
  @SerializedName("divname"          ) var divname          : String? = null,
  @SerializedName("divcode"          ) var divcode          : String? = null,
  @SerializedName("dtname"           ) var dtname           : String? = null,
  @SerializedName("dtcode"           ) var dtcode           : Int?    = null,
  @SerializedName("lho"              ) var lho              : String? = null,
  @SerializedName("module"           ) var module           : String? = null,
  @SerializedName("rbo"              ) var rbo              : String? = null,
  @SerializedName("koid"             ) var koid             : String? = null,
  @SerializedName("sdp_name"         ) var sdpName          : String? = null,
  @SerializedName("sdpHaddr"         ) var sdpHaddr         : String? = null,
  @SerializedName("sdpBaddr"         ) var sdpBaddr         : String? = null,
  @SerializedName("landlineno"       ) var landlineno       : String? = null,
  @SerializedName("mobno"            ) var mobno            : String? = null,
  @SerializedName("pancard"          ) var pancard          : String? = null,
  @SerializedName("sdpacc"           ) var sdpacc           : String? = null,
  @SerializedName("bankname"         ) var bankname         : String? = null,
  @SerializedName("brname"           ) var brname           : String? = null,
  @SerializedName("brcode"           ) var brcode           : String? = null,
  @SerializedName("ifsccode"         ) var ifsccode         : String? = null,
  @SerializedName("banktype"         ) var banktype         : String? = null,
  @SerializedName("entryDatetime"    ) var entryDatetime    : String? = null,
  @SerializedName("center"           ) var center           : String? = null,
  @SerializedName("adhar"            ) var adhar            : String? = null,
  @SerializedName("lb_name"          ) var lbName           : String? = null,
  @SerializedName("lbCode"           ) var lbCode           : Int?    = null,
  @SerializedName("kioType"          ) var kioType          : String? = null,
  @SerializedName("cifNo"            ) var cifNo            : String? = null,
  @SerializedName("modificationDate" ) var modificationDate : String? = null,
  @SerializedName("accountType"      ) var accountType      : String? = null,
  @SerializedName("dUpdateDate"      ) var dUpdateDate      : String? = null,
  @SerializedName("remark"           ) var remark           : String? = null,
  @SerializedName("acc_s"            ) var accS             : String? = null,
  @SerializedName("acc_brcd_s"       ) var accBrcdS         : String? = null,
  @SerializedName("limitHold"        ) var limitHold        : Int?    = null,
  @SerializedName("sdpFname"         ) var sdpFname         : String? = null,
  @SerializedName("sdpLoc"           ) var sdpLoc           : String? = null,
  @SerializedName("homePin"          ) var homePin          : String? = null,
  @SerializedName("busPin"           ) var busPin           : String? = null,
  @SerializedName("agrdone"          ) var agrdone          : String? = null,
  @SerializedName("userId"           ) var userId           : String? = null

)