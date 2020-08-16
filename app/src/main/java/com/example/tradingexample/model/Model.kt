package com.example.tradingexample.model

import java.io.Serializable

data class CurrentRate(var name: String, var rate: Double, var rateChange: Double) : Serializable