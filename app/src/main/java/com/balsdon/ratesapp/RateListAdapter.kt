package com.balsdon.ratesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.balsdon.ratesapp.model.RateItem
import com.balsdon.ratesapp.rateItem.RateItemView

class RateListAdapter(private var ratesList: List<RateItem>) : RecyclerView.Adapter<RateListAdapter.RateItemViewHolder>() {
    companion object {
        private const val BASE_RATE_INDEX = 0
    }

    class RateItemViewHolder(val rateItemView: RateItemView) : RecyclerView.ViewHolder(rateItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateItemViewHolder {
        val view  = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_item_rate, parent, false)
                as RateItemView
        return RateItemViewHolder(view)
    }

    override fun getItemCount(): Int = ratesList.size

    override fun onBindViewHolder(holder: RateItemViewHolder, position: Int) {
        holder.rateItemView.setRate(ratesList[position], ratesList[BASE_RATE_INDEX].rate)
    }

    fun update(ratesList: List<RateItem>) {
        this.ratesList = ratesList
        this.notifyDataSetChanged()
    }
}