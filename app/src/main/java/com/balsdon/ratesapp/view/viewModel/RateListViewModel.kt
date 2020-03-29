package com.balsdon.ratesapp.view.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.balsdon.ratesapp.dataBroker.DataBroker
import com.balsdon.ratesapp.dataBroker.RateListResult
import com.balsdon.ratesapp.model.RateItem

class RateListViewModel(private val dataBroker: DataBroker)  : ViewModel() {

    companion object {
        const val DEFAULT_CODE = "EUR"
        const val DEFAULT_RATE = 1.0
        const val MAX_INPUT_LENGTH = 10
    }

    private val rateListResult = MutableLiveData<RateListResult>()
    private var currentCode =
        DEFAULT_CODE

    fun getRateList(): LiveData<RateListResult> = rateListResult

    fun unsubscribe() = dataBroker.unsubscribe()

    fun refresh(rateItem: RateItem = RateItem(
        DEFAULT_CODE,
        DEFAULT_RATE
    )) {
        currentCode = rateItem.currencyCode
        dataBroker.subscribeToRates(currentCode) { result ->
            rateListResult.postValue(result)
            when (result) {
                is RateListResult.Empty,
                is RateListResult.Error -> {
                    dataBroker.unsubscribe()
                }
            }
        }
    }
}