package com.balsdon.ratesapp.dataBroker

//TODO: Move this to a build variant that is not prod

class OfflineBroker : DataBroker {
    override fun getRates(): RateListResult = RateListResult.Empty
}