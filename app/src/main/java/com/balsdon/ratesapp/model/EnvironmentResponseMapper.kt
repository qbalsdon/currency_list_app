package com.balsdon.ratesapp.model

interface EnvironmentResponseMapper {
    fun toRateResponse(): RateResponse
    fun isEmpty(): Boolean
}