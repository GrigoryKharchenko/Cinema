package kinopoisk.cinema.presentation.screen.searchfilter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kinopoisk.cinema.di.IoDispatcher
import kinopoisk.cinema.domain.repository.SearchFilterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchFilterViewModel @AssistedInject constructor(
    private val searchFilterRepository: SearchFilterRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @Assisted private val typeFilterScreen: TypeFilterScreen
) : ViewModel() {

    private val _searchFilterUiState = MutableStateFlow<SearchFilterUiState>(SearchFilterUiState.Loading)
    val searchFilterUiState = _searchFilterUiState.asStateFlow()

    private var genresOrCountries: List<SearchFilterModel> = emptyList()

    init {
        getFilters()
    }

    private fun getFilters() {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                when (typeFilterScreen.typeFilter) {
                    TypeFilter.COUNTRY -> searchFilterRepository.getCountry()
                    else -> searchFilterRepository.getGenre()
                }
            }.onSuccess { searchFilter ->
                genresOrCountries = searchFilter
                _searchFilterUiState.emit(SearchFilterUiState.Success(searchFilter))
            }.onFailure {
                _searchFilterUiState.emit(SearchFilterUiState.Error)
            }
        }
    }

    fun getQuery(query: String) {
        viewModelScope.launch {
            _searchFilterUiState.emit(
                SearchFilterUiState.Success(
                    genresOrCountries.filter {
                        it.countryOrGenre.contains(query, true)
                    })
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(typeFilterScreen: TypeFilterScreen): SearchFilterViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            typeFilterScreen: TypeFilterScreen
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(typeFilterScreen) as T
            }
        }
    }
}
