package com.example.tradingexample

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tradingexample.activity.BaseActivity
import com.example.tradingexample.adapter.CurrentRateAdapter
import com.example.tradingexample.di.Injection
import com.example.tradingexample.model.CurrentRate
import com.example.tradingexample.viewmodel.CurrentRateViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : BaseActivity() {
    private lateinit var viewModel: CurrentRateViewModel
    private lateinit var adapter: CurrentRateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
        initView()

        viewModel.loadCurrentRate(keyArray)
        loadCurrentRate()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory()
        ).get(CurrentRateViewModel::class.java)

        viewModel.currentRatesArrayList.observe(this, renderCurrentRates)
        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
        viewModel.isEmptyList.observe(this, emptyListObserver)
    }

    private fun initView() {
        adapter = CurrentRateAdapter(viewModel.currentRatesArrayList.value ?: arrayListOf())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private val renderCurrentRates = Observer<ArrayList<CurrentRate>> {
        Log.d(TAG, "data updated $it")
        adapter.update(it)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        Log.d(TAG, "isViewLoading $it")
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.d(TAG, "onMessageError $it")
    }

    private val emptyListObserver = Observer<Boolean> {
        Log.d(TAG, "emptyListObserver $it")
    }

    private fun loadCurrentRate() {
        val job: Job = GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                adapter.updateList()
                delay(10000)
            }
        }
    }

    companion object {
        const val TAG = "MainActivity"
        val keyArray = arrayOf(
            "AUDUSD",
            "EURGBP",
            "EURUSD",
            "GBPUSD",
            "NZDUSD",
            "USDAED",
            "USDAFN",
            "USDALL",
            "USDAMD",
            "USDANG",
            "USDAOA",
            "USDARS",
            "USDATS",
            "USDAUD",
            "USDAWG",
            "USDAZM",
            "USDAZN",
            "USDBAM",
            "USDBBD",
            "USDBDT",
            "USDBEF",
            "USDBGN",
            "USDBHD",
            "USDBIF",
            "USDBMD",
            "USDBND",
            "USDBOB",
            "USDBRL",
            "USDBSD",
            "USDBTN",
            "USDBWP",
            "USDBYN",
            "USDBYR",
            "USDBZD",
            "USDCAD",
            "USDCDF",
            "USDCHF",
            "USDCLP",
            "USDCNH",
            "USDCNY",
            "USDCOP",
            "USDCRC",
            "USDCUC",
            "USDCUP",
            "USDCVE",
            "USDCYP",
            "USDCZK",
            "USDDEM",
            "USDDJF",
            "USDDKK",
            "USDDOP",
            "USDDZD",
            "USDEEK",
            "USDEGP",
            "USDERN",
            "USDESP",
            "USDETB",
            "USDEUR",
            "USDFIM",
            "USDFJD",
            "USDFKP",
            "USDFRF",
            "USDGBP",
            "USDGEL",
            "USDGGP",
            "USDGHC",
            "USDGHS",
            "USDGIP",
            "USDGMD",
            "USDGNF",
            "USDGRD",
            "USDGTQ",
            "USDGYD",
            "USDHKD",
            "USDHNL",
            "USDHRK",
            "USDHTG",
            "USDHUF",
            "USDIDR",
            "USDIEP",
            "USDILS",
            "USDIMP",
            "USDINR",
            "USDIQD",
            "USDIRR",
            "USDISK",
            "USDITL",
            "USDJEP",
            "USDJMD",
            "USDJOD",
            "USDJPY",
            "USDKES",
            "USDKGS",
            "USDKHR",
            "USDKMF",
            "USDKPW",
            "USDKRW",
            "USDKWD",
            "USDKYD",
            "USDKZT",
            "USDLAK",
            "USDLBP",
            "USDLKR",
            "USDLRD",
            "USDLSL",
            "USDLTL",
            "USDLUF",
            "USDLVL",
            "USDLYD",
            "USDMAD",
            "USDMDL",
            "USDMGA",
            "USDMGF",
            "USDMKD",
            "USDMMK",
            "USDMNT",
            "USDMOP",
            "USDMRO",
            "USDMRU",
            "USDMTL",
            "USDMUR",
            "USDMVR",
            "USDMWK",
            "USDMXN",
            "USDMYR",
            "USDMZM",
            "USDMZN",
            "USDNAD",
            "USDNGN",
            "USDNIO",
            "USDNLG",
            "USDNOK",
            "USDNPR",
            "USDNZD",
            "USDOMR",
            "USDPAB",
            "USDPEN",
            "USDPGK",
            "USDPHP",
            "USDPKR",
            "USDPLN",
            "USDPTE",
            "USDPYG",
            "USDQAR",
            "USDROL",
            "USDRON",
            "USDRSD",
            "USDRUB",
            "USDRWF",
            "USDSAR",
            "USDSBD",
            "USDSCR",
            "USDSDD",
            "USDSDG",
            "USDSEK",
            "USDSGD",
            "USDSHP",
            "USDSIT",
            "USDSKK",
            "USDSLL",
            "USDSOS",
            "USDSPL",
            "USDSRD",
            "USDSRG",
            "USDSTD",
            "USDSTN",
            "USDSVC",
            "USDSYP",
            "USDSZL",
            "USDTHB",
            "USDTJS",
            "USDTMM",
            "USDTMT",
            "USDTND",
            "USDTOP",
            "USDTRL",
            "USDTRY",
            "USDTTD",
            "USDTVD",
            "USDTWD",
            "USDTZS",
            "USDUAH",
            "USDUGX",
            "USDUSD",
            "USDUYU",
            "USDUZS",
            "USDVAL",
            "USDVEB",
            "USDVEF",
            "USDVES",
            "USDVND",
            "USDVUV",
            "USDWST",
            "USDXAF",
            "USDXAG",
            "USDXAU",
            "USDXBT",
            "USDXCD",
            "USDXDR",
            "USDXOF",
            "USDXPD",
            "USDXPF",
            "USDXPT",
            "USDYER",
            "USDZAR",
            "USDZMK",
            "USDZMW",
            "USDZWD"
        )
    }
}