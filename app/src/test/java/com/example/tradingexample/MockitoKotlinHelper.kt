package com.example.tradingexample

import org.mockito.ArgumentMatchers.*
import org.mockito.ArgumentCaptor

fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()

fun <T : Any> safeEq(value: T): T = eq(value) ?: value