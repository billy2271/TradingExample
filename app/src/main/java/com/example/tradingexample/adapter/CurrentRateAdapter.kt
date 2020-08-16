package com.example.tradingexample.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tradingexample.R
import com.example.tradingexample.model.CurrentRate
import kotlinx.android.synthetic.main.stock_item.view.*
import java.text.DecimalFormat
import java.util.*

class CurrentRateAdapter(private var currentRates: ArrayList<CurrentRate>) : RecyclerView.Adapter<CurrentRateAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stock_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        currentRates[position].let {
            viewHolder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return currentRates.size
    }

    fun update(data: ArrayList<CurrentRate>) {
        currentRates = data
        notifyDataSetChanged()
    }

    fun updateList() {
        var newCurrentRatesArray: ArrayList<CurrentRate> = arrayListOf()

        for (currentRate in currentRates) {
            val random = Random()
            val randomChange = 0.001 + (0.010 - 0.001) * random.nextDouble()
            if ((0..1).random() == 0) {
                currentRate.rateChange = randomChange
            } else {
                currentRate.rateChange = -randomChange
            }
            currentRate.rate += randomChange

            newCurrentRatesArray.add(currentRate)
        }
        currentRates.clear()
        currentRates.addAll(newCurrentRatesArray)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tradingTitle: TextView = view.tradingTitle
        private val tradingChange: TextView = view.tradingChange
        private val tradingSell: TextView = view.tradingSell
        private val tradingBuy: TextView = view.tradingBuy
        private val tradingSubTitle: TextView = view.tradingSubTitle
        private val tradingImage: ImageView = view.tradingImage

        @SuppressLint("ResourceAsColor")
        fun bind(currentRate: CurrentRate) {
            currentRate.let {
                val random = Random()
                val randomSell = 0.001 + (0.010 - 0.001) * random.nextDouble()
                val randomBuy = 0.001 + (0.010 - 0.001) * random.nextDouble()
                var randomChange = (it.rate + it.rateChange) / it.rate / 100

                val colorIncrease = "#3CDE5A"
                val colorDecrease = "#FF3768"
                val colorNormal = "#FFFFFF"

                when {
                    it.rateChange > 0 -> {
                        itemView.tradingImage.setImageResource(R.drawable.ic_trend_increase)
                        tradingChange.setTextColor(Color.parseColor(colorIncrease))
                    }
                    it.rateChange == 0.0 -> {
                        randomChange = 0.0
                        tradingChange.setTextColor(Color.parseColor(colorNormal))
                    }
                    else -> {
                        itemView.tradingImage.setImageResource(R.drawable.ic_trend_decrease)
                        tradingChange.setTextColor(Color.parseColor(colorDecrease))
                    }
                }

                val name = it.name.substring(0, 3) + "/" + it.name.substring(3)
                tradingTitle.tradingTitle.text = name
                tradingChange.text = DecimalFormat("0.0000%").format(randomChange)
                tradingSell.text = DecimalFormat("##.####").format(it.rate - randomSell)
                tradingBuy.text = DecimalFormat("##.####").format(it.rate + randomBuy)
                tradingSubTitle.text = "$name : Forex"

            }

        }
    }
}