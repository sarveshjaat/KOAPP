package com.nictko.services.responseother

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("user_id"              ) var userId            : String? = null,
  @SerializedName("office"               ) var office            : String? = null,
  @SerializedName("region"               ) var region            : String? = null,
  @SerializedName("dtname"               ) var dtname            : String? = null,
  @SerializedName("br_code"              ) var brCode            : String? = null,
  @SerializedName("br_name"              ) var brName            : String? = null,
  @SerializedName("sdp_name"             ) var sdpName           : String? = null,
  @SerializedName("location"             ) var location          : String? = null,
  @SerializedName("loc_status"           ) var locStatus         : String? = null,
  @SerializedName("mobno"                ) var mobno             : String? = null,
  @SerializedName("cc_acc_op"            ) var ccAccOp           : String? = null,
  @SerializedName("fd_cc"                ) var fdCc              : String? = null,
  @SerializedName("rd_cc"                ) var rdCc              : String? = null,
  @SerializedName("pmsby"                ) var pmsby             : String? = null,
  @SerializedName("pmjjby"               ) var pmjjby            : String? = null,
  @SerializedName("aadharseeding"        ) var aadharseeding     : String? = null,
  @SerializedName("mobileseeding"        ) var mobileseeding     : String? = null,
  @SerializedName("wd_cc"                ) var wdCc              : String? = null,
  @SerializedName("dep_cc"               ) var depCc             : String? = null,
  @SerializedName("ft_cc"                ) var ftCc              : String? = null,
  @SerializedName("chq_Issue"            ) var chqIssue          : String? = null,
  @SerializedName("chq_stop"             ) var chqStop           : String? = null,
  @SerializedName("bbps"                 ) var bbps              : String? = null,
  @SerializedName("pass_prnt"            ) var passPrnt          : String? = null,
  @SerializedName("com_sms_alerts"       ) var comSmsAlerts      : String? = null,
  @SerializedName("com_deb_card_blocked" ) var comDebCardBlocked : String? = null,
  @SerializedName("com_pmjdyod"          ) var comPmjdyod        : String? = null,
  @SerializedName("com_neft"             ) var comNeft           : String? = null,
  @SerializedName("com_apy"              ) var comApy            : String? = null,
  @SerializedName("loan_lead_gen"        ) var loanLeadGen       : String? = null,
  @SerializedName("recovery"             ) var recovery          : String? = null,
  @SerializedName("tot_pay"              ) var totPay            : String? = null,
  @SerializedName("nict_share"           ) var nictShare         : String? = null,
  @SerializedName("cap_share"            ) var capShare          : String? = null,
  @SerializedName("fin_amt"              ) var finAmt            : String? = null,
  @SerializedName("bill_date"            ) var billDate          : String? = null,
  @SerializedName("selected_date"        ) var selectedDate      : String? = null,
  @SerializedName("rup_Activation"       ) var rupActivation     : String? = null,
  @SerializedName("imps"                 ) var imps              : String? = null,
  @SerializedName("rup_crdIssue"         ) var rupCrdIssue       : String? = null,
  @SerializedName("tds"                  ) var tds               : String? = null,
  @SerializedName("incentive"            ) var incentive         : String? = null,
  @SerializedName("adjustment"           ) var adjustment        : String? = null

)