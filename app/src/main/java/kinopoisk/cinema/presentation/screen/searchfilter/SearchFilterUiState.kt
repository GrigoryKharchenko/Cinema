package kinopoisk.cinema.presentation.screen.searchfilter

sealed interface SearchFilterUiState {

    object Loading : SearchFilterUiState
    object Error : SearchFilterUiState
    data class Success(
        val countryOrGenre: List<SearchFilterModel>
    ) : SearchFilterUiState
}
