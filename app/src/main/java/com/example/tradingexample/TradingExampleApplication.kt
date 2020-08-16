package com.example.tradingexample

import android.app.Application
import android.content.Context

class TradingExampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

    }

    companion object {
        lateinit var context: Context
    }
}