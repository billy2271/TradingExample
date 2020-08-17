package com.example.tradingexample.model

/**
 * The model class to store current rate in.
 *
 * @param name the name of the current rate
 * @param rate the rate of the current
 * @param rateChange the rate change of the current
 */
data class CurrentRate(var name: String, var rate: Double, var rateChange: Double)