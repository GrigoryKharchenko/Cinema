package kinopoisk.cinema.presentation.screen.filmdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kinopoisk.cinema.di.IoDispatcher
import kinopoisk.cinema.domain.DetailFilmRepository
import kinopoisk.cinema.presentation.screen.filmdetail.model.ErrorUiModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.FilmDetailUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmDetailViewModel @Inject constructor(
    private val detailFilmRepository: DetailFilmRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<FilmDetailUiState>(FilmDetailUiState.Loading)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    fun getFilmDetail(id: Int) {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                detailFilmRepository.getFilmDetail(id)
            }.onSuccess { filmDetailModel ->
                _uiStateFlow.emit(
                    FilmDetailUiState.Success(
                        filmDetailUiModel = FilmDetailUiModel(
                            detailFilm = filmDetailModel,
                        )
                    )
                )
                getActors(id)
                getStuff(id)
                getGallery(id)
                getSimilar(id)
            }.onFailure {
                _uiStateFlow.emit(
                    FilmDetailUiState.Error(
                        errors = ErrorUiModel(
                            isVisibleTextError = true,
                            isVisibleAppBar = false,
                            isVisibleNestedScroll = false
                        )
                    )
                )
            }
        }
    }

    private fun getActors(id: Int) {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                detailFilmRepository.getActor(id).filter { actor ->
                    actor.profession == TypeStuff.ACTOR.name
                }
            }.onSuccess { actor ->
                _uiStateFlow.update { uiState ->
                    (uiState as? FilmDetailUiState.Success)?.copy(
                        filmDetailUiModel = uiState.filmDetailUiModel.copy(
                            actor = actor,
                            isVisibleTitleActor = actor.isNotEmpty(),
                            isVisibleCountActor = actor.isNotEmpty(),
                        )
                    ) ?: uiState
                }
            }.onFailure {
                _uiStateFlow.emit(
                    FilmDetailUiState.Error(
                        ErrorUiModel(
                            isVisibleTitleActor = false,
                            isVisibleCountActor = false,
                        )
                    )
                )
            }
        }
    }

    private fun getStuff(id: Int) {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                detailFilmRepository.getStuff(id).filter { actor ->
                    actor.profession != TypeStuff.ACTOR.name
                }
            }.onSuccess { stuff ->
                _uiStateFlow.update { uiState ->
                    (uiState as? FilmDetailUiState.Success)?.copy(
                        filmDetailUiModel = uiState.filmDetailUiModel.copy(
                            stuff = stuff,
                            isVisibleTitleStuff = stuff.isNotEmpty(),
                            isVisibleCountStuff = stuff.isNotEmpty(),
                        )
                    ) ?: uiState
                }
            }.onFailure {
                _uiStateFlow.emit(
                    FilmDetailUiState.Error(
                        ErrorUiModel(
                            isVisibleTitleStuff = false,
                            isVisibleCountStuff = false,
                        )
                    )
                )
            }
        }
    }

    private fun getGallery(id: Int) {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                detailFilmRepository.getGallery(id)
            }.onSuccess { gallery ->
                _uiStateFlow.update { uiState ->
                    (uiState as? FilmDetailUiState.Success)?.copy(
                        filmDetailUiModel = uiState.filmDetailUiModel.copy(
                            gallery = gallery,
                            isVisibleTitleGallery = gallery.isNotEmpty(),
                            isVisibleCountGallery = gallery.isNotEmpty(),
                        )
                    ) ?: uiState
                }
            }.onFailure {
                _uiStateFlow.emit(
                    FilmDetailUiState.Error(
                        ErrorUiModel(
                            isVisibleTitleGallery = false,
                            isVisibleCountGallery = false,
                        )
                    )
                )
            }
        }
    }

    private fun getSimilar(id: Int) {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                detailFilmRepository.getSimilar(id)
            }.onSuccess { similar ->
                _uiStateFlow.update { uiState ->
                    (uiState as? FilmDetailUiState.Success)?.copy(
                        filmDetailUiModel = uiState.filmDetailUiModel.copy(
                            similar = similar,
                            isVisibleTitleSimilar = similar.isNotEmpty(),
                            isVisibleCountSimilar = similar.isNotEmpty(),
                        )
                    ) ?: uiState
                }
            }.onFailure {
                _uiStateFlow.emit(
                    FilmDetailUiState.Error(
                        ErrorUiModel(
                            isVisibleTitleSimilar = false,
                            isVisibleCountSimilar = false,
                        )
                    )
                )
            }
        }
    }
}
