package com.balsdon.ratesapp.rateItem

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.balsdon.ratesapp.R
import com.balsdon.ratesapp.model.RateItem
import kotlinx.android.synthetic.main.item_rate.view.*

class RateItemView(context: Context, attributeSet: AttributeSet) : ConstraintLayout(context, attributeSet), RateItemViewable {

    init {
        inflate(context, R.layout.item_rate, this)
    }

    private val presenter: RateItemPresenter by lazy {
        RateItemPresenter(this)
    }

    fun setRate(rateItem: RateItem, multiplier: Double = 1.0) =
        presenter.setRate(rateItem, multiplier)

    override fun setIcon(drawableResourceInt: Int) =
        list_item_rate_currency_flag.setImageDrawable(context.getDrawable(drawableResourceInt))

    override fun setCurrencyCode(code: String) {
        list_item_rate_currency_iso.text = code
    }

    override fun setCurrencyName(stringResourceInt: Int) {
        list_item_rate_currency_name.text = context.getString(stringResourceInt)
    }

    override fun setCurrencyRate(currencyRate: String) =
        list_item_rate_currency_rate.setText(currencyRate)

    fun disableTextEntry() {
        list_item_rate_currency_rate.isEnabled = false
    }
}