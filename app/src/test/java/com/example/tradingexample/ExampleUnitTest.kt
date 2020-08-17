package com.example.tradingexample

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.tradingexample.data.OperationCallback
import com.example.tradingexample.model.CurrentRate
import com.example.tradingexample.model.CurrentRateDataSource
import com.example.tradingexample.viewmodel.CurrentRateViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class ExampleUnitTest {

    @Mock
    private lateinit var repository: CurrentRateDataSource

    @Mock
    private lateinit var context: Application

    @Captor
    private lateinit var operationCallbackCaptor: ArgumentCaptor<OperationCallback<CurrentRate?>>

    private lateinit var viewModel: CurrentRateViewModel

    private lateinit var isViewLoadingObserver: Observer<Boolean>
    private lateinit var onMessageErrorObserver: Observer<Any>
    private lateinit var emptyListObserver: Observer<Boolean>
    private lateinit var onRenderCurrentRatesObserver: Observer<ArrayList<CurrentRate>>

    private lateinit var currentRateEmptyList: ArrayList<CurrentRate>
    private lateinit var currentRateList: ArrayList<CurrentRate>
    private lateinit var mockKeyArray: Array<String>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`<Context>(context.applicationContext).thenReturn(context)
        mockData()

        viewModel = CurrentRateViewModel(repository)

        setupObservers()
    }

    @Test
    fun `retrieve currentRateList with ViewModel and Repository returns empty list`() {
        with(viewModel) {
            loadCurrentRate(mockKeyArray)
            isViewLoading.observeForever(isViewLoadingObserver)
            isEmptyList.observeForever(emptyListObserver)
            currentRatesArrayList.observeForever(onRenderCurrentRatesObserver)
        }

        verify(repository, times(1)).retrieveCurrentRates(safeEq(mockKeyArray), capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(currentRateEmptyList)

        Assert.assertNotNull(viewModel.isViewLoading.value)
        Assert.assertTrue(viewModel.isEmptyList.value == true)
        Assert.assertTrue(viewModel.currentRatesArrayList.value?.size == 0)
    }

    @Test
    fun `retrieve CurrentRates with ViewModel and Repository return data`() {
        with(viewModel) {
            loadCurrentRate(mockKeyArray)
            isViewLoading.observeForever(isViewLoadingObserver)
            currentRatesArrayList.observeForever(onRenderCurrentRatesObserver)
        }

        verify(repository, times(1)).retrieveCurrentRates(safeEq(mockKeyArray), capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(currentRateList)

        Assert.assertNotNull(viewModel.isViewLoading.value)
        Assert.assertTrue(viewModel.currentRatesArrayList.value?.size == 3)
    }

    @Test
    fun `retrieve CurrentRates with ViewModel and Repository returns an error`() {
        with(viewModel) {
            loadCurrentRate(mockKeyArray)
            isViewLoading.observeForever(isViewLoadingObserver)
            onMessageError.observeForever(onMessageErrorObserver)
        }
        verify(repository, times(1)).retrieveCurrentRates(safeEq(mockKeyArray), capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onError("An error occurred")
        Assert.assertNotNull(viewModel.isViewLoading.value)
        Assert.assertNotNull(viewModel.onMessageError.value)
    }

    private fun setupObservers() {
        onRenderCurrentRatesObserver = mock(Observer::class.java) as Observer<ArrayList<CurrentRate>>
        isViewLoadingObserver = mock(Observer::class.java) as Observer<Boolean>
        onMessageErrorObserver = mock(Observer::class.java) as Observer<Any>
        emptyListObserver = mock(Observer::class.java) as Observer<Boolean>
    }

    private fun mockData() {
        currentRateEmptyList = arrayListOf()
        val mockList: ArrayList<CurrentRate> = arrayListOf()
        mockList.add(CurrentRate("USDJPY", 106.588499, 0.0))
        mockList.add(CurrentRate("EURGBP", 0.905169, 0.0))
        mockList.add(CurrentRate("AUDUSD", 0.716892, 0.0))
        currentRateList = mockList

        mockKeyArray = arrayOf("USDJPY", "EURGBP", "AUDUSD")
    }
}