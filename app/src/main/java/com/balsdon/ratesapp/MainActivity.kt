package com.balsdon.ratesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.balsdon.ratesapp.dataBroker.DataBroker
import com.balsdon.ratesapp.dataBroker.RequiresDataBroker

class MainActivity : AppCompatActivity(), RequiresDataBroker, SubscribesToObservers {

    private lateinit var dataBroker: DataBroker

    override fun setDataBroker(dataBroker: DataBroker) {
        this.dataBroker = dataBroker
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this, RateListModelFactory(dataBroker)).get(RateListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun registerObservers() {

    }

    override fun unregisterObservers() {

    }
}
