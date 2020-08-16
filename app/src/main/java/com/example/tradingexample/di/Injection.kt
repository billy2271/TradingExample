package com.example.tradingexample.di

import androidx.lifecycle.ViewModelProvider
import com.example.tradingexample.model.CurrentRateDataSource
import com.example.tradingexample.model.CurrentRateRepository
import com.example.tradingexample.viewmodel.ViewModelFactory

object Injection {
    private val currentRateDataSource: CurrentRateDataSource = CurrentRateRepository()
    private val currentRateViewModelFactory = ViewModelFactory(currentRateDataSource)

    fun providerRepository(): CurrentRateDataSource {
        return currentRateDataSource
    }

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return currentRateViewModelFactory
    }
}