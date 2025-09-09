package com.nictko.services.request

import com.google.gson.annotations.SerializedName


data class AddComplaintReqmodel(

    @SerializedName("state") var state: String? = null,
    @SerializedName("division") var division: String? = null,
    @SerializedName("dist") var dist: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("mobile") var mobile: String? = null,
    @SerializedName("alt_mobile") var alt_mobile: String? = null,
    @SerializedName("serviceName") var serviceName: String? = null,
    @SerializedName("serviceId") var serviceId: String? = null,
    @SerializedName("msg") var msg: String? = null,
    @SerializedName("bankName") var bankName: String? = null,
    @SerializedName("compCat") var compCat: String? = null,
    @SerializedName("cspCat") var cspCat: String? = null

)