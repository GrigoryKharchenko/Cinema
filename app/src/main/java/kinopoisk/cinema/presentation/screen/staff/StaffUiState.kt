package kinopoisk.cinema.presentation.screen.staff

import kinopoisk.cinema.presentation.screen.filmdetail.model.StaffModel

sealed interface StaffUiState {

    object Error : StaffUiState
    object Loading : StaffUiState
    data class Success(
        val staff: List<StaffModel>
    ) : StaffUiState
}
