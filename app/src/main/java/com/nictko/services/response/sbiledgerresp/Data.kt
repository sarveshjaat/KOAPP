package com.nictko.services.response.sbiledgerresp

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("circle_name" ) var circleName : String? = null,
  @SerializedName("csp_code"    ) var cspCode    : String? = null,
  @SerializedName("csp_name"    ) var cspName    : String? = null,
  @SerializedName("txn_cnt"     ) var txnCnt     : String? = null,
  @SerializedName("trans_type"  ) var transType  : String? = null,
  @SerializedName("comm_amt"    ) var commAmt    : String? = null,
  @SerializedName("csp_pay"     ) var cspPay     : String? = null

)