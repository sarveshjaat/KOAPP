package com.nictko.services.api

import com.nictko.services.LimitReportResponse
import com.nictko.services.response.settlementresponse.AddSettlementResponse
import com.nictko.services.SettlementReportResponse
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
import com.nictko.services.response.VersionResponse
import com.nictko.services.response.compliantlistresponse.CompliantListResposne
import com.nictko.services.response.feedbackresposne.AddFeedbackResponse
import com.nictko.services.response.loginresponseother.LoginResponseOther
import com.nictko.services.response.otpresendresponse.OTPResendResponse
import com.nictko.services.response.otpresponse.OTPVerifyResponse
import com.nictko.services.response.sbiledgerresp.SBIBankingLedgerResponse
import com.nictko.services.response.showdocumentresp.AllBankShowDocumentResponse
import com.nictko.services.response.shownotice.ShowNoticeResponse
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
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {

    @POST("KoPortal/api/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("KoPortal/api/add_settlement_request")
    suspend fun addsettlemt(@Body addSettlementReq: AddSettlementRequest): Response<AddSettlementResponse>

    @POST("KoPortal/api/settlement_report")
    suspend fun settlemtreport(@Body VSettlementReportReq: SettlementReportRequest): Response<SettlementReportResponse>
    @POST("KoPortal/api/limit_report")
    suspend fun limitreport(@Body VlimitReqReport: LimitReportRequest): Response<LimitReportResponse>

    @POST("KoPortal/api/add_complaint_request")
    suspend fun addcomplaint(@Body addComplaintRequserapi: AddComplaintReqmodel): Response<AddComplaintResponse>

    @POST("KoPortal/api/add_limit_hold_request")
    suspend fun addlimihold(@Body addlimitholduserapi: LimitHoldRequest): Response<LimitHoldResponse>

    @GET("KoPortal/api/app_version")
    suspend fun getversiondetials(): Response<VersionResponse>

//    @GET("KoPortal/api/show_notice")
//    suspend fun getshownoticedetails(): Response<ShowNoticeResponse>

    @POST("KoPortal/api/verify-otp")
    suspend fun loginotpverify(@Body otpverifyrequest: OTPVerifyRequest): Response<OTPVerifyResponse>


    @POST("KoPortal/api/resend_otp")
    suspend fun otpresend(@Body otpResendRquest: OTPResendRquest): Response<OTPResendResponse>


    //other bank login otp

    @POST("KoPortal/api/login_bank")
    suspend fun loginUserother(@Body loginRequestother: LoginRequestOther): Response<LoginResponseOther>


    @POST("KoPortal/api/verify_otp_bank")
    suspend fun loginotpverify_other(@Body otpverifyrequest: OTPVerifyOtherRequest): Response<OTPVerifyOtherResponse>


    @POST("KoPortal/api/resend_otp_bank")
    suspend fun otpresend_other(@Body otpResendRquest: ResendOTPVerifyOtherRequest): Response<ResendOTPVerifyOtherResponse>


   //complaintand feedback

    @POST("KoPortal/api/add_complaint_request")
    suspend fun addcomplaint_other(@Body addComplaintRequserapi: AddComplaintReqmodel): Response<AddComplaintResponse>


    @POST("KoPortal/api/complaint_report")
    suspend fun complaintlist_onlysbi(@Body complaintlisrrq: ComplaintReportListRequest): Response<CompliantListResposne>

    @POST("KoPortal/api/add_complaint_feedback")
    suspend fun addfeedback_onlysbi(@Body addfeedbackreq: AddComplaintFeedbackRequest): Response<AddFeedbackResponse>

    @POST("GetCspCategory")
    suspend fun cspcategory_onlysbi(@Body cspcategory: GetCspCategoryRequest): Response<GetCspCategoryResponse>


    @POST("KoPortal/api/show_notice")
    suspend fun getshownoticedetails_allbank(@Body allbankshownotice: AllBankShowNoticeRequest): Response<ShowNoticeResponse>

    @POST("KoPortal/api/show_document")
    suspend fun getshowdocument_allbank(@Body allbankshownotice: AllBankShowNoticeRequest): Response<AllBankShowDocumentResponse>

//ledger
    @POST("KoPortal/api/ledger/bob")
    suspend fun interface_boblegder(@Body repobobrequest: BOBBankLedgerRequest): Response<BOBBankingLedgerResponse>

    @POST("KoPortal/api/ledger/bom")
    suspend fun interface_bomlegder(@Body repobomrequest: BOMBankLedgerRequest): Response<BOMBankingLedgerResponse>

    @POST("KoPortal/api/ledger/boi")
    suspend fun interface_boilegder(@Body repoboirequest: BOIBankingLedgerRequest): Response<BOIBankingLedgerResponse>

    @POST("KoPortal/api/ledger/pnb")
    suspend fun interface_pnblegder(@Body repopnbrequest: PNBBankingLedgerRequest): Response<PNBBankingLedgerResponse>

    @POST("KoPortal/api/ledger/bupg")
    suspend fun interface_bupglegder(@Body repobupgrequest: BUPGBankingLedgerRequest): Response<BUPGBankingLedgerResponse>

    @POST("KoPortal/api/ledger/mgb")
    suspend fun interface_mgblegder(@Body repomgbrequest: MGBBankingLedgerRequest): Response<MGBBankingLedgerResponse>

    @POST("KoPortal/api/ledger/mpgb")
    suspend fun interface_mpgblegder(@Body repompgbrequest: MPGBBankingLedger): Response<MPGBBankingLedgerResponse>

    @POST("KoPortal/api/ledger/crgb")
    suspend fun interface_crgblegder(@Body repocrgbbrequest: CRGBBankingLedgerRequest): Response<CRGBBankingLedgerResposne>

    @POST("KoPortal/api/ledger/bggb")
    suspend fun interface_bggblegder(@Body repobggbbrequest: BGGBBankingLedgerRequest): Response<BGGBBankingLedgerResponsne>

    @POST("KoPortal/api/ledger/ubin")
    suspend fun interface_ubinblegder(@Body repoubinreq: UBINBankLedgerRequest): Response<UBINBankingLedgerResponse>

    @POST("KoPortal/api/ledger/sbin")
    suspend fun interface_sbiblegder(@Body reposbireq: SBINBankingLedgerRequest): Response<SBIBankingLedgerResponse>


    @POST("csp_report/GetDetails")
    suspend fun getcspdetails_bank(@Body cspdetailsreqt: BankingCspDetailsRequest): Response<BankingCSPDetailsResponse>


    companion object {
        fun getApi(): UserApi? {
            return ApiClient.client?.create(UserApi::class.java)
        }
        fun getApiCateory(): UserApi? {
            return ApiGetCategory.client?.create(UserApi::class.java)
        }
        fun getApicspCateory(): UserApi? {
            return ApiGetCspDetails.client?.create(UserApi::class.java)
        }
    }

}
