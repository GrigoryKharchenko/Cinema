package kinopoisk.cinema.presentation.screen.profilepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kinopoisk.cinema.data.mapper.mapToFilmInteresting
import kinopoisk.cinema.data.mapper.mapToFilmsViewedModel
import kinopoisk.cinema.di.IoDispatcher
import kinopoisk.cinema.domain.repository.FilmInterestingRepository
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
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val filmInterestingRepository: FilmInterestingRepository
) : ViewModel() {

    private val _filmViewedFlow = MutableStateFlow<List<TypeCardCategoryUiModel>>(emptyList())
    val filmViewedFlow = _filmViewedFlow.asStateFlow()

    private val _filmInterestingFlow = MutableStateFlow<List<TypeCardCategoryUiModel>>(emptyList())
    val filmInterestingFlow = _filmInterestingFlow.asStateFlow()

    init {
        subscribeViewedFilm()
        subscribeInterestingFilm()
    }

    fun deleteAllViewedFilm() {
        viewModelScope.launch(ioDispatcher) {
            filmViewedRepository.deleteAllFilms()
        }
    }

    fun deleteAllInterestingFilm() {
        viewModelScope.launch(ioDispatcher) {
            filmInterestingRepository.deleteAllFilms()
        }
    }

    private fun subscribeViewedFilm() {
        filmViewedRepository.subscribeToReceive().map { filmsEntity ->
            filmsEntity.mapToFilmsViewedModel()
        }.onEach { filmViewedUiModels ->
            _filmViewedFlow.emit(filmViewedUiModels)
        }.launchIn(viewModelScope)
    }

    private fun subscribeInterestingFilm() {
        filmInterestingRepository.subscribeToReceive().map { filmsEntity ->
            filmsEntity.mapToFilmInteresting()
        }.onEach { filmViewedUiModels ->
            _filmInterestingFlow.emit(filmViewedUiModels)
        }.launchIn(viewModelScope)
    }
}
