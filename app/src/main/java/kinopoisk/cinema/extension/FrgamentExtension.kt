package kinopoisk.cinema.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow

fun <T> Fragment.launchWhenStarted(flow: Flow<T>, action: suspend (T) -> Unit) {
    flow.launchWhenStarted(lifecycleScope, viewLifecycleOwner.lifecycle, action)
}
