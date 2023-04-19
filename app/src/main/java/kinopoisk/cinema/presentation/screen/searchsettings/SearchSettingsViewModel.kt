package kinopoisk.cinema.presentation.screen.searchsettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kinopoisk.cinema.domain.repository.PeriodRepository
import kinopoisk.cinema.presentation.screen.period.PeriodModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchSettingsViewModel @Inject constructor(
    private val periodRepository: PeriodRepository
) : ViewModel() {

    private val _yearFlow = MutableStateFlow<SearchSettingsUiState>(SearchSettingsUiState.Init())
    val yearFlow = _yearFlow.asStateFlow()

    init {
        getYear()
    }

    private fun getYear() {
        viewModelScope.launch {
            periodRepository.yearFlow.collect { period ->
                _yearFlow.emit(SearchSettingsUiState.Initialized(PeriodModel(period.startPeriod, period.endPeriod)))
            }
        }
    }
}
