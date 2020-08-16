package com.example.tradingexample.data

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName


data class CurrentRateResponse(
    @SerializedName("rates") val rates: JsonObject?,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String
) {
    fun isSuccess(): Boolean = (code == 200)
}