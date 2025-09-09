package com.nictko.services.repository

import com.nictko.services.LimitReportResponse
import com.nictko.services.response.settlementresponse.AddSettlementResponse
import com.nictko.services.SettlementReportResponse
import com.nictko.services.api.UserApi
import com.nictko.services.request.AddComplaintFeedbackRequest
import com.nictko.services.request.AddComplaintReqmodel
import com.nictko.services.request.AddSettlementRequest
import com.nictko.services.request.AllBankShowNoticeRequest
import com.nictko.services.request.ComplaintReportListRequest
import com.nictko.services.request.GetCspCategoryRequest
import com.nictko.services.request.LimitHoldRequest
import com.nictko.services.request.LimitReportRequest
import com.nictko.services.request.LoginRequest
import com.nictko.services.request.LoginRequestOther
import com.nictko.services.request.OTPResendRquest
import com.nictko.services.request.OTPVerifyRequest
import com.nictko.services.request.SBINBankingLedgerRequest
import com.nictko.services.request.SettlementReportRequest
import com.nictko.services.request.requestother.BGGBBankingLedgerRequest
import com.nictko.services.request.requestother.BOBBankLedgerRequest
import com.nictko.services.request.requestother.BOIBankingLedgerRequest
import com.nictko.services.request.requestother.BOMBankLedgerRequest
import com.nictko.services.request.requestother.BUPGBankingLedgerRequest
import com.nictko.services.request.requestother.BankingCspDetailsRequest
import com.nictko.services.request.requestother.CRGBBankingLedgerRequest
import com.nictko.services.request.requestother.MGBBankingLedgerRequest
import com.nictko.services.request.requestother.MPGBBankingLedger
import com.nictko.services.request.requestother.OTPVerifyOtherRequest
import com.nictko.services.request.requestother.PNBBankingLedgerRequest
import com.nictko.services.request.requestother.ResendOTPVerifyOtherRequest
import com.nictko.services.request.requestother.UBINBankLedgerRequest
import com.nictko.services.response.AddComplaintResponse
import com.nictko.services.response.BankingCSPDetailsResponse
import com.nictko.services.response.GetCspCategoryResponse
import com.nictko.services.response.LimitHoldResponse
import com.nictko.services.response.LoginResponse
import com.nictko.services.response.shownotice.ShowNoticeResponse
import com.nictko.services.response.VersionResponse
import com.nictko.services.response.compliantlistresponse.CompliantListResposne
import com.nictko.services.response.feedbackresposne.AddFeedbackResponse
import com.nictko.services.response.loginresponseother.LoginResponseOther
import com.nictko.services.response.otpresendresponse.OTPResendResponse
import com.nictko.services.response.otpresponse.OTPVerifyResponse
import com.nictko.services.response.sbiledgerresp.SBIBankingLedgerResponse
import com.nictko.services.response.showdocumentresp.AllBankShowDocumentResponse
import com.nictko.services.responseother.BOBBankingLedgerResponse
import com.nictko.services.responseother.OTPVerifyOtherResponse
import com.nictko.services.responseother.bggbledgerresponse.BGGBBankingLedgerResponsne
import com.nictko.services.responseother.boilledgerresponse.BOIBankingLedgerResponse
import com.nictko.services.responseother.bomledgerresponse.bomledgerresponse.BOMBankingLedgerResponse
import com.nictko.services.responseother.bupgledgerresp.BUPGBankingLedgerResponse
import com.nictko.services.responseother.crgbledgerrespponse.CRGBBankingLedgerResposne
import com.nictko.services.responseother.mgbledgerresponse.MGBBankingLedgerResponse
import com.nictko.services.responseother.mpgbledgerresponse.MPGBBankingLedgerResponse
import com.nictko.services.responseother.pnbledgerresposne.PNBBankingLedgerResponse
import com.nictko.services.responseother.resendotpother.ResendOTPVerifyOtherResponse
import com.nictko.services.responseother.ubinledgerresponse.UBINBankingLedgerResponse
import retrofit2.Response

class UserRepository {

    suspend fun loginUserrepo(loginRequest: LoginRequest): Response<LoginResponse>? {
        return  UserApi.getApi()?.loginUser(loginRequest = loginRequest)
    }


    suspend fun addsettlementrepo(addSettlementRequest: AddSettlementRequest): Response<AddSettlementResponse>? {
        return  UserApi.getApi()?.addsettlemt(addSettlementReq = addSettlementRequest)
    }

    suspend fun settlmentreportrepo(ViewSettlementReportReq: SettlementReportRequest): Response<SettlementReportResponse>? {
        return  UserApi.getApi()?.settlemtreport(VSettlementReportReq = ViewSettlementReportReq)
    }
    suspend fun limitreportrepo(ViewlimitReportReq: LimitReportRequest): Response<LimitReportResponse>? {
        return  UserApi.getApi()?.limitreport(VlimitReqReport = ViewlimitReportReq)
    }
    suspend fun addcomplainrepo(addComplaintReqmodelrepo: AddComplaintReqmodel): Response<AddComplaintResponse>? {
        return  UserApi.getApi()?.addcomplaint(addComplaintRequserapi = addComplaintReqmodelrepo)
    }

    suspend fun addlimitholdrepo(addlimitholdrepo: LimitHoldRequest): Response<LimitHoldResponse>? {
        return  UserApi.getApi()?.addlimihold(addlimitholduserapi = addlimitholdrepo)
    }

    suspend fun versionrepo():Response<VersionResponse>? {
        return  UserApi.getApi()?.getversiondetials()
    }
//    suspend fun shownoticerepo():Response<ShowNoticeResponse>? {
//        return  UserApi.getApi()?.getshownoticedetails()
//    }

    suspend fun otpUserrepo(otpverifyrequest: OTPVerifyRequest): Response<OTPVerifyResponse>? {
        return  UserApi.getApi()?.loginotpverify(otpverifyrequest = otpverifyrequest)
    }


    suspend fun otpresendUserrepo(otpResendRquest: OTPResendRquest): Response<OTPResendResponse>? {
        return  UserApi.getApi()?.otpresend(otpResendRquest = otpResendRquest)
    }


    //other bank

    suspend fun loginUserotherrepo(loginRequestother: LoginRequestOther): Response<LoginResponseOther>? {
        return  UserApi.getApi()?.loginUserother(loginRequestother = loginRequestother)
    }


    suspend fun otpUserrepo_otpother(otpverifyrequest: OTPVerifyOtherRequest): Response<OTPVerifyOtherResponse>? {
        return  UserApi.getApi()?.loginotpverify_other(otpverifyrequest = otpverifyrequest)
    }

    suspend fun otpresendUserrepo_other(otpResendRquest: ResendOTPVerifyOtherRequest): Response<ResendOTPVerifyOtherResponse>? {
        return  UserApi.getApi()?.otpresend_other(otpResendRquest = otpResendRquest)
    }


    suspend fun bobledgerrepo(bobrequest: BOBBankLedgerRequest): Response<BOBBankingLedgerResponse>? {
        return  UserApi.getApi()?.interface_boblegder(repobobrequest = bobrequest)
    }

    suspend fun bomledgerrepo(bomrequest: BOMBankLedgerRequest): Response<BOMBankingLedgerResponse>? {
        return  UserApi.getApi()?.interface_bomlegder(repobomrequest = bomrequest)
    }

    suspend fun addcomplainrepo_other(addComplaintReqmodelrepo: AddComplaintReqmodel): Response<AddComplaintResponse>? {
        return  UserApi.getApi()?.addcomplaint_other(addComplaintRequserapi = addComplaintReqmodelrepo)
    }

    suspend fun addcomplainlist_repoonlysbi(complaintlistonlysbi: ComplaintReportListRequest): Response<CompliantListResposne>? {
        return  UserApi.getApi()?.complaintlist_onlysbi(complaintlisrrq = complaintlistonlysbi)

    }
    suspend fun addfeednack_repoonlysbi(addfeedbackreq: AddComplaintFeedbackRequest): Response<AddFeedbackResponse>? {
        return  UserApi.getApi()?.addfeedback_onlysbi(addfeedbackreq = addfeedbackreq)

    }

    suspend fun cspcategory_repoonlysbi(getcspcategory: GetCspCategoryRequest): Response<GetCspCategoryResponse>? {
        return  UserApi.getApiCateory()?.cspcategory_onlysbi(cspcategory = getcspcategory)

    }


    suspend fun allbankshownotice_repo(allbankshownoticereq : AllBankShowNoticeRequest): Response<ShowNoticeResponse>? {
        return  UserApi.getApi()?.getshownoticedetails_allbank(allbankshownotice = allbankshownoticereq)

    }
    suspend fun allbankshowdocument_repo(allbankshownoticereq : AllBankShowNoticeRequest): Response<AllBankShowDocumentResponse>? {
        return  UserApi.getApi()?.getshowdocument_allbank(allbankshownotice = allbankshownoticereq)

    }

    suspend fun boiledgerrepo(boirequest: BOIBankingLedgerRequest): Response<BOIBankingLedgerResponse>? {
        return  UserApi.getApi()?.interface_boilegder(repoboirequest = boirequest)
    }

    suspend fun pnbledgerrepo(pnbrequest: PNBBankingLedgerRequest): Response<PNBBankingLedgerResponse>? {
        return  UserApi.getApi()?.interface_pnblegder(repopnbrequest = pnbrequest)
    }

    suspend fun bupgledgerrepo(bupgrequest: BUPGBankingLedgerRequest): Response<BUPGBankingLedgerResponse>? {
        return  UserApi.getApi()?.interface_bupglegder(repobupgrequest = bupgrequest)
    }

    suspend fun mgbledgerrepo(mgbgrequest: MGBBankingLedgerRequest): Response<MGBBankingLedgerResponse>? {
        return  UserApi.getApi()?.interface_mgblegder(repomgbrequest = mgbgrequest)
    }

    suspend fun mpgbledgerrepo(mpgbgrequest: MPGBBankingLedger): Response<MPGBBankingLedgerResponse>? {
        return  UserApi.getApi()?.interface_mpgblegder(repompgbrequest = mpgbgrequest)
    }

    suspend fun crgbledgerrepo(crgbbrequest: CRGBBankingLedgerRequest): Response<CRGBBankingLedgerResposne>? {
        return  UserApi.getApi()?.interface_crgblegder(repocrgbbrequest = crgbbrequest)
    }

    suspend fun bggbledgerrepo(bggbbbrequest: BGGBBankingLedgerRequest): Response<BGGBBankingLedgerResponsne>? {
        return  UserApi.getApi()?.interface_bggblegder(repobggbbrequest = bggbbbrequest)
    }

    suspend fun ubinledgerrepo(ubinbrequest: UBINBankLedgerRequest): Response<UBINBankingLedgerResponse>? {
        return  UserApi.getApi()?.interface_ubinblegder(repoubinreq = ubinbrequest)
    }

    suspend fun sbiledgerrepo(sbibrequest: SBINBankingLedgerRequest): Response<SBIBankingLedgerResponse>? {
        return  UserApi.getApi()?.interface_sbiblegder(reposbireq = sbibrequest)
    }

    suspend fun cspbankingdeatils_repo(cspbankingdtlreq : BankingCspDetailsRequest): Response<BankingCSPDetailsResponse>? {
        return  UserApi.getApicspCateory()?.getcspdetails_bank(cspdetailsreqt = cspbankingdtlreq)

    }

}