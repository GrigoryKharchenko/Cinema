package kinopoisk.cinema.extension

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kinopoisk.cinema.R

private const val CHIP_STROKE_WIDTH = 1.0f
private const val CHIP_PADDING_HORIZONTAL = 36.0f
private const val FIRST_CHIP = 1
private const val SECOND_CHIP = 2

fun ViewGroup.inflate(layoutId: Int): View {
    return LayoutInflater.from(context).inflate(layoutId, this, false)
}

fun ChipGroup.addChip(context: Context, chipNumber: Int, isFirst: Boolean, onClickChip: (Int) -> Unit) {
    Chip(context).apply {
        id = View.generateViewId()
        text = chipNumber.toString()
        setTextColor(resources.getColorStateList(R.color.chip_text, null))
        chipBackgroundColor = resources.getColorStateList(R.color.chip_background, null)
        chipStrokeWidth = CHIP_STROKE_WIDTH
        chipStrokeColor = resources.getColorStateList(R.color.chip_stroke, null)
        isCheckable = true
        isChecked = isFirst
        chipStartPadding = CHIP_PADDING_HORIZONTAL
        chipEndPadding = CHIP_PADDING_HORIZONTAL
        isCheckedIconVisible = false
        addView(this)
        setOnClickListener {
            onClickChip(chipNumber)
        }
    }
}

fun Fragment.addChipGroup(context: Context, chipGroup: ChipGroup, countChip: Int, onClickChip: (Int) -> Unit) {
    chipGroup.addChip(context, FIRST_CHIP, true, onClickChip)
    (SECOND_CHIP..countChip).forEach {
        chipGroup.addChip(context, it, false, onClickChip)
    }
}
