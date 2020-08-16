package com.example.tradingexample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tradingexample.data.OperationCallback
import com.example.tradingexample.model.CurrentRate
import com.example.tradingexample.model.CurrentRateDataSource

class CurrentRateViewModel(private val currentRateDataSource: CurrentRateDataSource) : ViewModel() {
    private val _currentRates =
        MutableLiveData<ArrayList<CurrentRate>>().apply { value = arrayListOf() }
    val currentRatesArrayList: LiveData<ArrayList<CurrentRate>> = _currentRates

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    fun loadCurrentRate(name: Array<String>) {
        _isViewLoading.postValue(true)
        currentRateDataSource.retrieveCurrentRates(
            name = name,
            callback = object : OperationCallback<CurrentRate?> {
                override fun onSuccess(data: ArrayList<CurrentRate>) {
                    _isViewLoading.postValue(false)
                    if (data.isEmpty()) {
                        _isEmptyList.postValue(true)
                    } else {
                        _currentRates.value = data
                    }
                }

                override fun onError(error: String?) {
                    _isViewLoading.postValue(false)
                    _onMessageError.postValue(error)
                }
            })
    }

}