package com.example.tradingexample.model

import com.example.tradingexample.data.OperationCallback

interface CurrentRateDataSource {
    fun retrieveCurrentRates(name: Array<String>, callback: OperationCallback<CurrentRate?>)
    fun cancel()
}