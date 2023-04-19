package kinopoisk.cinema.presentation.customview

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.ItemPeriodBinding
import kinopoisk.cinema.presentation.screen.period.YearModel
import kinopoisk.cinema.presentation.screen.period.adapter.PeriodAdapter
import java.util.Calendar

private const val PERIOD_YEARS = 12

class Period @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var periodEndYear = Calendar.getInstance().get(Calendar.YEAR)

    private var selectedYear: Int? = null

    private var _yearPeriod = 0
    val yearPeriod get() = _yearPeriod

    private val adapter = PeriodAdapter(onYearClick = { year ->
        selectedYear = if (selectedYear == year) null else year
        updateYearsData()
        _yearPeriod = year
    })
    private var binding: ItemPeriodBinding

    init {
        inflate(context, R.layout.item_period, this)
        binding = ItemPeriodBinding.bind(rootView)
        with(binding) {
            rvYears.adapter = adapter
            ivBack.setOnClickListener {
                backYears()
            }
            ivNext.setOnClickListener {
                nextYears()
            }
        }
        updateYearsData()
    }

    private fun nextYears() {
        periodEndYear += PERIOD_YEARS
        updateYearsData()
    }

    private fun backYears() {
        periodEndYear -= PERIOD_YEARS
        updateYearsData()
    }

    private fun updateYearsData() {
        val years = mutableListOf<YearModel>()
        for (i in 11 downTo 0) {
            val year = periodEndYear - i
            years.add(YearModel(year = year, isSelected = year == selectedYear))
        }
        setYears(years)
    }

    private fun setYears(years: List<YearModel>) {
        with(binding) {
            if (years.isNotEmpty()) {
                tvPeriod.text = context.getString(R.string.period_start_end, years.first().year, years.last().year)
            } else {
                tvPeriod.text = ""
            }
        }
        adapter.submitList(years)
    }
}
