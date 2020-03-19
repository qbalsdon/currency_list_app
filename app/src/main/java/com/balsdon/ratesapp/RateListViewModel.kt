package com.balsdon.ratesapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.balsdon.ratesapp.model.RateItem

class RateListViewModel  : ViewModel() {
    private val rateList: MutableLiveData<List<RateItem>> by lazy {
        MutableLiveData<List<RateItem>>().also {
            loadRateList()
        }
    }

    fun getRateList(): LiveData<List<RateItem>> {
        return rateList
    }

    private fun loadRateList() {
        // Do an asynchronous operation to fetch data
    }
}