package com.example.tradingexample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tradingexample.model.CurrentRateDataSource

class ViewModelFactory(private val repository: CurrentRateDataSource) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentRateViewModel(repository) as T
    }
}