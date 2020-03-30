package com.balsdon.ratesapp.model

import java.io.Serializable

data class RateItem(val currencyCode: String, val rate: Double = 1.0) : Serializable