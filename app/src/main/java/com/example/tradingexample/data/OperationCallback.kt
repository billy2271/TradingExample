package com.example.tradingexample.data

import com.example.tradingexample.model.CurrentRate

interface OperationCallback<T> {
    fun onSuccess(data: ArrayList<CurrentRate>)
    fun onError(error: String?)
}