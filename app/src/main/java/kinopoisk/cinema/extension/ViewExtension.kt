package kinopoisk.cinema.extension

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kinopoisk.cinema.R

private const val CHIP_STROKE_WIDTH = 1.0f
private const val CHIP_PADDING_HORIZONTAL = 36.0f

fun ViewGroup.inflate(layoutId: Int): View {
    return LayoutInflater.from(context).inflate(layoutId, this, false)
}

fun ChipGroup.addChip(context: Context, label: String, isFirst: Boolean, onClickChip: (String) -> Unit) {
    Chip(context).apply {
        id = View.generateViewId()
        text = label
        setTextColor(resources.getColorStateList(R.color.chip_text, null))
        chipBackgroundColor = resources.getColorStateList(R.color.chip_background, null)
        chipStrokeWidth = CHIP_STROKE_WIDTH
        chipStrokeColor = resources.getColorStateList(R.color.chip_stroke, null)
        isClickable = true
        isCheckable = true
        isChecked = isFirst
        chipStartPadding = CHIP_PADDING_HORIZONTAL
        chipEndPadding = CHIP_PADDING_HORIZONTAL
        isCheckedIconVisible = false
        isFocusable = true
        addView(this)
        setOnClickListener {
            onClickChip(label)
        }
    }
}

fun Fragment.addChipGroup(context: Context, chipGroup: ChipGroup, countChip: Int, onClickChip: (String) -> Unit) {
    for (i in 1..countChip) {
        if (i == 1) {
            chipGroup.addChip(context, i.toString(), true, onClickChip)
        } else {
            chipGroup.addChip(context, i.toString(), false, onClickChip)
        }
    }
}
