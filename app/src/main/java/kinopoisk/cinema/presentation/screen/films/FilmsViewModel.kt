package kinopoisk.cinema.presentation.screen.films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kinopoisk.cinema.data.TypeCategories
import kinopoisk.cinema.domain.repository.FilmsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FilmsViewModel @AssistedInject constructor(
    private val filmsPageSource: FilmsRepository,
    @Assisted private val typeCategory: TypeCategories
) : ViewModel() {

    val filmsFlow: StateFlow<PagingData<FilmModel>> =
        Pager(PagingConfig(pageSize = 20, enablePlaceholders = false)) {
            filmsPageSource.getAll(typeCategory)
        }.flow
            .cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    @AssistedFactory
    interface Factory {
        fun create(typeCategory: TypeCategories): FilmsViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            typeCategory: TypeCategories
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(typeCategory) as T
            }
        }
    }
}
