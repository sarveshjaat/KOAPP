package com.nictko.services.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiGetCspDetails {

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    var mOkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(logging)
        .build()

    var mRetrofit: Retrofit? = null


    val gson = GsonBuilder().setLenient().create()

    val client: Retrofit?
        get() {
            if(mRetrofit == null){
                mRetrofit = Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL2)
                    .client(mOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return mRetrofit
        }
}