package com.example.tradingexample.model

import android.util.Log
import com.example.tradingexample.data.ApiClient
import com.example.tradingexample.data.CurrentRateResponse
import com.example.tradingexample.data.OperationCallback
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val TAG = "CurrentRateRepository"

class CurrentRateRepository : CurrentRateDataSource {
    private var call: Call<CurrentRateResponse>? = null
    override fun retrieveCurrentRates(nameArray: Array<String>, callback: OperationCallback<CurrentRate?>) {
        val nameKey = getNameKey(nameArray)
        call = ApiClient.build()?.currentRates(nameKey)
        call?.enqueue(object : Callback<CurrentRateResponse> {
            override fun onFailure(call: Call<CurrentRateResponse>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<CurrentRateResponse>, response: Response<CurrentRateResponse>) {
                Log.d(TAG, "${response.body()}")
                response.body()?.let { it ->
                    if (response.isSuccessful && (it.isSuccess())) {

                        val ratesArrayList = arrayListOf<CurrentRate>()
                        var i = 0
                        it.rates?.let {
                            for ((key, value1) in it.entrySet()) {
                                val value: CurrentRate = Gson().fromJson(value1, CurrentRate::class.java)
                                value.name = nameArray[i]
                                value.rateChange = 0.0
                                i++
                                ratesArrayList.add(value)
                            }
                        }
                        callback.onSuccess(ratesArrayList)
                    } else {
                        callback.onError(it.message)
                    }
                }
            }
        })
    }

    override fun cancel() {
        call?.cancel()
    }

    private fun getNameKey(nameArray: Array<String>): String {
        var nameKey = ""
        if (nameArray.size > 1) {
            for (i in nameArray.indices) {
                nameKey += nameArray[i]
                nameKey += if (i != nameArray.size) {
                    ","
                } else ""
            }
        } else {
            nameKey = nameArray[0]
        }
        return nameKey
    }
}