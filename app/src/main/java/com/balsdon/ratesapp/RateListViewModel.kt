package com.balsdon.ratesapp

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
    private val rateList: MutableLiveData<RateListResult> by lazy {
        MutableLiveData<RateListResult>().also {
            loadRateList()
        }
    }

    fun getRateList(): LiveData<RateListResult> = rateList

    private fun loadRateList():RateListResult {
        // Do an asynchronous operation to fetch data
        return dataBroker.getRates()
    }
}