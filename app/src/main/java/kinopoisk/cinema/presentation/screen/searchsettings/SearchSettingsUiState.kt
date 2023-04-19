package kinopoisk.cinema.presentation.screen.searchsettings

import androidx.annotation.StringRes
import kinopoisk.cinema.R
import kinopoisk.cinema.presentation.screen.period.PeriodModel

sealed interface SearchSettingsUiState {

    data class Init(
        @StringRes val period: Int = R.string.search_setting_chosen_year
    ) : SearchSettingsUiState

    data class Initialized(
        val period: PeriodModel
    ) : SearchSettingsUiState
}
