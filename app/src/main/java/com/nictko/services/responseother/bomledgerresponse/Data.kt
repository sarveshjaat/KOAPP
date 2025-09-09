package com.nictko.services.responseother.bomledgerresponse

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("bca_id"                      ) var bcaId                   : String? = null,
  @SerializedName("total_no_of_aeps_txn"        ) var totalNoOfAepsTxn        : String? = null,
  @SerializedName("amt_of_aeps_txn"             ) var amtOfAepsTxn            : String? = null,
  @SerializedName("total_aeps_commission"       ) var totalAepsCommission     : String? = null,
  @SerializedName("less_ineligible_txn_count"   ) var lessIneligibleTxnCount  : String? = null,
  @SerializedName("less_ineligible_comm"        ) var lessIneligibleComm      : String? = null,
  @SerializedName("final_eligible_txn_count"    ) var finalEligibleTxnCount   : String? = null,
  @SerializedName("final_eligible_comm"         ) var finalEligibleComm       : String? = null,
  @SerializedName("acct_opening"                ) var acctOpening             : String? = null,
  @SerializedName("acct_opening_commission"     ) var acctOpeningCommission   : String? = null,
  @SerializedName("apy_count"                   ) var apyCount                : String? = null,
  @SerializedName("apy_commission"              ) var apyCommission           : String? = null,
  @SerializedName("pmjjby_count"                ) var pmjjbyCount             : String? = null,
  @SerializedName("pmjjby_comm"                 ) var pmjjbyComm              : String? = null,
  @SerializedName("pmsby_count"                 ) var pmsbyCount              : String? = null,
  @SerializedName("pmsby_commission"            ) var pmsbyCommission         : String? = null,
  @SerializedName("total_billing"               ) var totalBilling            : String? = null,
  @SerializedName("eighty_of_total_billing_bca" ) var eightyOfTotalBillingBca : String? = null,
  @SerializedName("branch_code_tcs_all"         ) var branchCodeTcsAll        : String? = null,
  @SerializedName("branch_name_master_file"     ) var branchNameMasterFile    : String? = null,
  @SerializedName("zone_name_master_file"       ) var zoneNameMasterFile      : String? = null,
  @SerializedName("br_category"                 ) var brCategory              : String? = null,
  @SerializedName("invoice"                     ) var invoice                 : String? = null,
  @SerializedName("final_amt"                   ) var finalAmt                : String? = null

)