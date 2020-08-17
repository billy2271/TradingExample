package com.example.tradingexample.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object ApiClient {
    private val STOCK_API_URL = "https://www.freeforexapi.com/api/"

    private var servicesApiInterface: ServicesApiInterface? = null

    fun build(): ServicesApiInterface? {
        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(STOCK_API_URL)
            .addConverterFactory(GsonConverterFactory.create())

        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor())

        val retrofit: Retrofit = builder.client(httpClient.build()).build()
        servicesApiInterface = retrofit.create(
            ServicesApiInterface::class.java
        )
        return servicesApiInterface as ServicesApiInterface
    }

    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    /**
     * This service is based on the free forex api
     * https://www.freeforexapi.com/api/
     */
    interface ServicesApiInterface {
        @GET("live")
        fun currentRates(@Query("pairs") name: String): Call<CurrentRateResponse>
    }
}