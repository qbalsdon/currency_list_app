package com.balsdon.ratesapp.view.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.balsdon.ratesapp.dataBroker.DataBroker
import com.balsdon.ratesapp.dataBroker.RateListResult
import com.balsdon.ratesapp.model.RateItem

class RateListViewModel(private val dataBroker: DataBroker)  : ViewModel() {

    companion object {
        const val MAX_INPUT_LENGTH = 10
    }

    private val rateListResult = MutableLiveData<RateListResult>()

    fun getRateList(): LiveData<RateListResult> = rateListResult

    fun unsubscribe() = dataBroker.unsubscribe()

    private fun onResultReceived(result: RateListResult) {
        rateListResult.postValue(result)
        when (result) {
            is RateListResult.Empty,
            is RateListResult.Error -> {
                dataBroker.unsubscribe()
            }
        }
    }

    fun refresh(rateItem: RateItem) {
        dataBroker.subscribeToRates(rateItem.currencyCode, ::onResultReceived)
    }

    fun refresh() {
        dataBroker.subscribeToRates(::onResultReceived)
    }
}