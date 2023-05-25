package kinopoisk.cinema.presentation.screen.period

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kinopoisk.cinema.domain.repository.PeriodRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class PeriodViewModel @Inject constructor(
    private val periodRepository: PeriodRepository
) : ViewModel() {

    fun addYears(startYear: Int, endYear: Int) {
        viewModelScope.launch {
            periodRepository.addYear(PeriodModel(startYear, endYear))
        }
    }
}
