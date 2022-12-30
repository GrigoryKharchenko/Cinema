package kinopoisk.cinema.extension

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

fun <T> Flow<T>.launchWhenStarted(
    lifecycleCoroutineScope: LifecycleCoroutineScope,
    lifecycle: Lifecycle
) {
    lifecycleCoroutineScope.launchWhenStarted {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@launchWhenStarted.collect()
        }
    }
}
