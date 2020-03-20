package com.balsdon.ratesapp.rateItem.flagResource

import com.balsdon.ratesapp.R

class AndroidCountryResourceResolver : CountryResourceResolver {

    data class CurrencyPair(val countryIconResource: Int, val countryCurrencyName: Int)

    private val countryCodeResourceMap = mapOf(
        "AUD" to CurrencyPair(R.drawable.ic_australia, R.string.country_currency_aud),
        "BGN" to CurrencyPair(R.drawable.ic_bulgaria, R.string.country_currency_bgn),
        "BRL" to CurrencyPair(R.drawable.ic_brazil, R.string.country_currency_brl),
        "CAD" to CurrencyPair(R.drawable.ic_canada, R.string.country_currency_cad),
        "CHF" to CurrencyPair(R.drawable.ic_switzerland, R.string.country_currency_chf),
        "CNY" to CurrencyPair(R.drawable.ic_china, R.string.country_currency_cny),
        "CZK" to CurrencyPair(R.drawable.ic_czech_republic, R.string.country_currency_czk),
        "DKK" to CurrencyPair(R.drawable.ic_denmark, R.string.country_currency_dkk),
        "EUR" to CurrencyPair(R.drawable.ic_european_union, R.string.country_currency_european_union),
        "GBP" to CurrencyPair(R.drawable.ic_united_kingdom, R.string.country_currency_gbp),
        "HKD" to CurrencyPair(R.drawable.ic_hong_kong, R.string.country_currency_hkd),
        "HRK" to CurrencyPair(R.drawable.ic_croatia, R.string.country_currency_hrk),
        "HUF" to CurrencyPair(R.drawable.ic_hungary, R.string.country_currency_huf),
        "IDR" to CurrencyPair(R.drawable.ic_indonesia, R.string.country_currency_idr),
        "ILS" to CurrencyPair(R.drawable.ic_israel, R.string.country_currency_ils),
        "INR" to CurrencyPair(R.drawable.ic_india, R.string.country_currency_inr),
        "ISK" to CurrencyPair(R.drawable.ic_iceland, R.string.country_currency_isk),
        "JPY" to CurrencyPair(R.drawable.ic_japan, R.string.country_currency_jpy),
        "KRW" to CurrencyPair(R.drawable.ic_south_korea, R.string.country_currency_krw),
        "MXN" to CurrencyPair(R.drawable.ic_mexico, R.string.country_currency_mxn),
        "MYR" to CurrencyPair(R.drawable.ic_malaysia, R.string.country_currency_myr),
        "NOK" to CurrencyPair(R.drawable.ic_norway, R.string.country_currency_nok),
        "NZD" to CurrencyPair(R.drawable.ic_new_zealand, R.string.country_currency_nzd),
        "PHP" to CurrencyPair(R.drawable.ic_philippines, R.string.country_currency_php),
        "PLN" to CurrencyPair(R.drawable.ic_republic_of_poland, R.string.country_currency_pln),
        "RON" to CurrencyPair(R.drawable.ic_romania, R.string.country_currency_ron),
        "RUB" to CurrencyPair(R.drawable.ic_russia, R.string.country_currency_rub),
        "SEK" to CurrencyPair(R.drawable.ic_sweden, R.string.country_currency_sek),
        "SGD" to CurrencyPair(R.drawable.ic_singapore, R.string.country_currency_sgd),
        "THB" to CurrencyPair(R.drawable.ic_thailand, R.string.country_currency_thb),
        "USD" to CurrencyPair(R.drawable.ic_united_states_of_america, R.string.country_currency_usd),
        "ZAR" to CurrencyPair(R.drawable.ic_south_africa, R.string.country_currency_zar)
    )

    override fun getFlagIconResourceFromCode(code: String): Int =
        countryCodeResourceMap[code.trim().toUpperCase()]?.countryIconResource ?: R.drawable.ic_unknown

    override fun getCurrencyStringResourceFromCode(code: String): Int =
        countryCodeResourceMap[code.trim().toUpperCase()]?.countryCurrencyName ?: R.string.country_currency_unknown
}
