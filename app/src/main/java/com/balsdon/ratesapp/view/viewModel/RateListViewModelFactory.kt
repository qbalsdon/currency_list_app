package com.balsdon.ratesapp.view.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.balsdon.ratesapp.dataBroker.DataBroker

@Suppress("UNCHECKED_CAST")
class RateListModelFactory(private val dataBroker: DataBroker) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RateListViewModel(dataBroker) as T
    }
}