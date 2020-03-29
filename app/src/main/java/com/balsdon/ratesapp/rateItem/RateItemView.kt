package com.balsdon.ratesapp.rateItem

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.balsdon.ratesapp.R
import com.balsdon.ratesapp.model.RateItem
import kotlinx.android.synthetic.main.item_rate.view.*

class RateItemView(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet), RateItemViewable, TextWatcher {

    private val presenter: RateItemPresenter by lazy {
        RateItemPresenter(this)
    }

    init {
        inflate(context, R.layout.item_rate, this)
    }

    fun getRateItem(): RateItem = presenter.getRateItem()

    private var onMultiplierChanged: () -> Unit = {}

    fun setRate(rateItem: RateItem) =
        presenter.setRate(rateItem)

    fun setCurrency(rateItem: RateItem) =
        presenter.setCurrency(rateItem)

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

    override fun getMultiplierChanged(): () -> Unit {
        return onMultiplierChanged
    }

    fun setMultiplierChanged(function: () -> Unit) {
        onMultiplierChanged = function
    }

    fun setMaxInputLimit(limit: Int) {
        list_item_rate_currency_rate.filters = arrayOf(InputFilter.LengthFilter(limit))
    }

    fun enableTextEntry() {
        list_item_rate_currency_rate.apply {
            inputType = InputType.TYPE_CLASS_NUMBER or
                    InputType.TYPE_NUMBER_FLAG_DECIMAL or
                    InputType.TYPE_NUMBER_FLAG_SIGNED
            addTextChangedListener(this@RateItemView)
            isEnabled = true

        }
    }

    //region TextWatcher
    override fun afterTextChanged(p0: Editable?) {}
    override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        presenter.updateUserEntry(text.toString())
    }
    //endregion
}