package kinopoisk.cinema.presentation.screen.homepage


sealed interface HomeUiState {

    object Error : HomeUiState
    object Loading : HomeUiState
    data class Success(
        val films: List<CategoryUiModel>
    ) : HomeUiState
}
