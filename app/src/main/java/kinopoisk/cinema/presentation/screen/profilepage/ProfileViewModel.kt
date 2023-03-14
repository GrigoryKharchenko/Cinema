package kinopoisk.cinema.presentation.screen.profilepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kinopoisk.cinema.data.mapper.mapToFilmsViewedModel
import kinopoisk.cinema.di.IoDispatcher
import kinopoisk.cinema.domain.repository.FilmViewedRepository
import kinopoisk.cinema.presentation.screen.homepage.TypeCardCategoryUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val filmViewedRepository: FilmViewedRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _filmFlow = MutableStateFlow<List<TypeCardCategoryUiModel>>(emptyList())
    val filmFlow = _filmFlow.asStateFlow()

    init {
        subscribeFilm()
    }

    fun deleteAllFilm() {
        viewModelScope.launch(ioDispatcher) {
            filmViewedRepository.deleteAllFilms()
        }
    }

    private fun subscribeFilm() {
        filmViewedRepository.subscribeToReceive().map { filmsEntity ->
            filmsEntity.mapToFilmsViewedModel()
        }.onEach { filmViewedUiModels ->
            _filmFlow.emit(filmViewedUiModels)
        }.launchIn(viewModelScope)
    }
}
