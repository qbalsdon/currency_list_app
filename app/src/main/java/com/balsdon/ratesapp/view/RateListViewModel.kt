package com.balsdon.ratesapp.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.balsdon.ratesapp.dataBroker.DataBroker
import com.balsdon.ratesapp.dataBroker.RateListResult

class RateListModelFactory(private val dataBroker: DataBroker) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RateListViewModel(dataBroker) as T
    }
}

class RateListViewModel(private val dataBroker: DataBroker)  : ViewModel() {
    private val rateListResult = MutableLiveData<RateListResult>()

    fun getRateList(): LiveData<RateListResult> = rateListResult

    fun refresh() {
        dataBroker.subscribeToRates { result ->
            rateListResult.postValue(result)
            when (result) {
                is RateListResult.Empty,
                is RateListResult.Error -> {
                    dataBroker.unsubscribe()
                }
            }
        }
    }

    init {
        refresh()
    }
}