package kinopoisk.cinema.presentation.screen.filmdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kinopoisk.cinema.data.mapper.mapToFilmViewedModel
import kinopoisk.cinema.di.IoDispatcher
import kinopoisk.cinema.domain.repository.DetailFilmRepository
import kinopoisk.cinema.domain.repository.FilmViewedRepository
import kinopoisk.cinema.presentation.screen.filmdetail.model.FilmDetailUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// TODO Это пиздец
class FilmDetailViewModel @AssistedInject constructor(
    private val detailFilmRepository: DetailFilmRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @Assisted private val filmId: Int,
    private val filmViewedRepository: FilmViewedRepository
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<FilmDetailUiState>(FilmDetailUiState.Loading)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    init {
        getFilmDetail()
    }

    private fun getFilmDetail() {
        viewModelScope.launch(ioDispatcher) {
            detailFilmRepository.getFilmDetail(filmId)
                .onSuccess { filmDetailModel ->
                    _uiStateFlow.emit(
                        FilmDetailUiState.DetailFilm(
                            filmDetailUiModel = FilmDetailUiModel(
                                detailFilm = filmDetailModel,
                            )
                        )
                    )
                    getStaff()
                    getGallery()
                    getSimilar()
                    getEntity()
                }.onFailure {
                    _uiStateFlow.emit(
                        FilmDetailUiState.DetailFilm(
                            filmDetailUiModel = FilmDetailUiModel(
                                isVisibleTextError = true,
                                isVisibleAppBar = false,
                                isVisibleNestedScroll = false
                            )
                        )
                    )
                }
        }
    }

    private fun getStaff() {
        viewModelScope.launch(ioDispatcher) {
            detailFilmRepository.getStaff(filmId)
                .onSuccess { staff ->
                    val actors = staff.filter { it.profession == TypeStaff.ACTOR }
                    val filmmakers = staff.filter { it.profession != TypeStaff.ACTOR }
                    _uiStateFlow.update { uiState ->
                        (uiState as? FilmDetailUiState.DetailFilm)?.copy(
                            filmDetailUiModel = uiState.filmDetailUiModel.copy(
                                staff = filmmakers,
                                actor = actors,
                                isVisibleActors = actors.isNotEmpty(),
                                isVisibleStaff = filmmakers.isNotEmpty(),
                            )
                        ) ?: uiState
                    }
                }.onFailure {
                    _uiStateFlow.update { uiState ->
                        (uiState as? FilmDetailUiState.DetailFilm)?.copy(
                            filmDetailUiModel = FilmDetailUiModel(
                                isVisibleActors = false,
                                isVisibleStaff = false,
                            )
                        ) ?: uiState
                    }
                }
        }
    }

    private fun getGallery() {
        viewModelScope.launch(ioDispatcher) {
            detailFilmRepository.getGallery(filmId)
                .onSuccess { gallery ->
                    _uiStateFlow.update { uiState ->
                        (uiState as? FilmDetailUiState.DetailFilm)?.copy(
                            filmDetailUiModel = uiState.filmDetailUiModel.copy(
                                gallery = gallery,
                                isVisibleGallery = gallery.isNotEmpty(),
                            )
                        ) ?: uiState
                    }
                }.onFailure {
                    _uiStateFlow.update { uiState ->
                        (uiState as? FilmDetailUiState.DetailFilm)?.copy(
                            filmDetailUiModel = FilmDetailUiModel(
                                isVisibleGallery = false,
                            )
                        ) ?: uiState
                    }
                }
        }
    }

    private fun getSimilar() {
        viewModelScope.launch(ioDispatcher) {
            detailFilmRepository.getSimilar(filmId)
                .onSuccess { similar ->
                    _uiStateFlow.update { uiState ->
                        (uiState as? FilmDetailUiState.DetailFilm)?.copy(
                            filmDetailUiModel = uiState.filmDetailUiModel.copy(
                                similar = similar,
                                isVisibleSimilar = similar.isNotEmpty(),
                            )
                        ) ?: uiState
                    }
                }.onFailure {
                    _uiStateFlow.update { uiState ->
                        (uiState as? FilmDetailUiState.DetailFilm)?.copy(
                            filmDetailUiModel = FilmDetailUiModel(
                                isVisibleSimilar = false,
                            )
                        ) ?: uiState
                    }
                }
        }
    }

    private fun getEntity() {
        viewModelScope.launch(ioDispatcher) {
            detailFilmRepository.getFimEntity(filmId)
                .onSuccess { filmViewedRepository.insertFilm(it.mapToFilmViewedModel()) }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(filmId: Int): FilmDetailViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            filmId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(filmId) as T
            }
        }
    }
}
