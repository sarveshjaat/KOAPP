package com.nictko.services.api

import com.nictko.services.AddLimitRequestResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("KoPortal/api/add_limit_request")
    suspend fun uploadData(
        @Part photo1: MultipartBody.Part,
        @Part("koid") name: RequestBody,
        @Part("name") email: RequestBody,
        @Part("location") location: RequestBody,
        @Part("mobno") mobno: RequestBody,
        @Part("depDate") depdate: RequestBody,
        @Part("amt") amount: RequestBody,
        @Part("payMode") paymode: RequestBody,
        @Part("brname") brname: RequestBody,
        @Part("brcode") brcode: RequestBody,
        @Part("chqDate") checkdate: RequestBody,
        @Part("chqbank") checkbank: RequestBody,
        @Part("chqno") checkno: RequestBody,
        @Part("txnno") txtno: RequestBody

    ): Response<AddLimitRequestResponse>
    @Multipart
    @POST("KoPortal/api/add_limit_request")
    suspend fun uploadnoimageData(
        @Part("koid") name: RequestBody,
        @Part("name") email: RequestBody,
        @Part("location") location: RequestBody,
        @Part("mobno") mobno: RequestBody,
        @Part("depDate") depdate: RequestBody,
        @Part("amt") amount: RequestBody,
        @Part("payMode") paymode: RequestBody,
        @Part("brname") brname: RequestBody,
        @Part("brcode") brcode: RequestBody,
        @Part("chqDate") checkdate: RequestBody,
        @Part("chqbank") checkbank: RequestBody,
        @Part("chqno") checkno: RequestBody,
        @Part("txnno") txtno: RequestBody

    ): Response<AddLimitRequestResponse>


}