package kinopoisk.cinema.presentation.screen.searchpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import kinopoisk.cinema.domain.repository.SearchFilmRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class SearchViewModel @Inject constructor(
    private val searchFilmRepository: Provider<SearchFilmRepository>
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private var newPagingSource: PagingSource<*, *>? = null

    val films: StateFlow<PagingData<SearchModel>> = query
        .map(::newPager)
        .flatMapLatest { pager -> pager.flow }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private fun newPager(query: String): Pager<Int, SearchModel> {
        return Pager(PagingConfig(20, enablePlaceholders = false)) {
            val films = searchFilmRepository.get()
            films(query).also { newPagingSource = it }
        }
    }

    fun setQuery(query: String) {
        viewModelScope.launch {
            _query.emit(query)
        }
    }
}
