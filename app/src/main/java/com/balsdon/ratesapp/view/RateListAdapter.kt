package com.balsdon.ratesapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.balsdon.ratesapp.R
import com.balsdon.ratesapp.model.RateItem
import com.balsdon.ratesapp.rateItem.RateItemView

class RateListAdapter(
    private var ratesList: List<RateItem>,
    private val baseCurrencyChanged: (String) -> Unit
) : RecyclerView.Adapter<RateListAdapter.RateItemViewHolder>() {
    class RateItemViewHolder(val rateItemView: RateItemView) : RecyclerView.ViewHolder(rateItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateItemViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_item_rate, parent, false)
                as RateItemView
        view.disableTextEntry()
        return RateItemViewHolder(view)
    }

    override fun getItemCount(): Int = ratesList.size

    override fun onBindViewHolder(holder: RateItemViewHolder, position: Int) {
        holder.rateItemView.apply {
            setRate(ratesList[position])
            setOnClickListener { baseCurrencyChanged.invoke(ratesList[position].currencyCode) }
        }
    }

    fun updateList(ratesList: List<RateItem>) {
        this.ratesList = ratesList
        this.notifyDataSetChanged()
    }

    fun updateMultiplier() {
        this.notifyDataSetChanged()
    }
}