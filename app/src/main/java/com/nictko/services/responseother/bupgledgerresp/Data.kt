package com.nictko.services.responseother.bupgledgerresp

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("bc_agent_id"            ) var bcAgentId           : String? = null,
  @SerializedName("office"                 ) var office              : String? = null,
  @SerializedName("state"                  ) var state               : String? = null,
  @SerializedName("district"               ) var district            : String? = null,
  @SerializedName("zone"                   ) var zone                : String? = null,
  @SerializedName("br_name"                ) var brName              : String? = null,
  @SerializedName("lnk_br_code"            ) var lnkBrCode           : String? = null,
  @SerializedName("ur_rl"                  ) var urRl                : String? = null,
  @SerializedName("bc_name"                ) var bcName              : String? = null,
  @SerializedName("con_no"                 ) var conNo               : String? = null,
  @SerializedName("saving_acc_no"          ) var savingAccNo         : String? = null,
  @SerializedName("pan_no"                 ) var panNo               : String? = null,
  @SerializedName("csp_loc"                ) var cspLoc              : String? = null,
  @SerializedName("total_no_acc_open_nonf" ) var totalNoAccOpenNonf  : String? = null,
  @SerializedName("remun_of_acc_op_nonf"   ) var remunOfAccOpNonf    : String? = null,
  @SerializedName("total_no_acc_open_f"    ) var totalNoAccOpenF     : String? = null,
  @SerializedName("remun_of_acc_op_f"      ) var remunOfAccOpF       : String? = null,
  @SerializedName("rd_acc"                 ) var rdAcc               : String? = null,
  @SerializedName("rem_of_rd_acc"          ) var remOfRdAcc          : String? = null,
  @SerializedName("fd_acc"                 ) var fdAcc               : String? = null,
  @SerializedName("rem_of_fd_acc"          ) var remOfFdAcc          : String? = null,
  @SerializedName("tot_no_of_trans"        ) var totNoOfTrans        : String? = null,
  @SerializedName("remun_of_txn"           ) var remunOfTxn          : String? = null,
  @SerializedName("tot_no_of_pmjjby"       ) var totNoOfPmjjby       : String? = null,
  @SerializedName("remun_of_pmjjby"        ) var remunOfPmjjby       : String? = null,
  @SerializedName("apy"                    ) var apy                 : String? = null,
  @SerializedName("rem_of_apy"             ) var remOfApy            : String? = null,
  @SerializedName("tot_of_adhar_seed"      ) var totOfAdharSeed      : String? = null,
  @SerializedName("rem_adhar_seed"         ) var remAdharSeed        : String? = null,
  @SerializedName("tot_of_mob_seed"        ) var totOfMobSeed        : String? = null,
  @SerializedName("rem_mob_seed"           ) var remMobSeed          : String? = null,
  @SerializedName("tot_no_add_inc"         ) var totNoAddInc         : String? = null,
  @SerializedName("rem_of_add_inc"         ) var remOfAddInc         : String? = null,
  @SerializedName("total"                  ) var total               : String? = null,
  @SerializedName("csp_Share"              ) var cspShare            : String? = null,
  @SerializedName("igst"                   ) var igst                : String? = null,
  @SerializedName("bill_Amount"            ) var billAmount          : String? = null,
  @SerializedName("csp_TDS"                ) var cspTDS              : String? = null,
  @SerializedName("remuniration_Of_PMSBY"  ) var remunirationOfPMSBY : String? = null,
  @SerializedName("round_Tripping"         ) var roundTripping       : String? = null,
  @SerializedName("net_Payable_to_CSP"     ) var netPayableToCSP     : String? = null,
  @SerializedName("total_No_Of_PMSBY"      ) var totalNoOfPMSBY      : String? = null

)