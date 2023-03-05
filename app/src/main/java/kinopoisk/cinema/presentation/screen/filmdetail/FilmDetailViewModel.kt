package kinopoisk.cinema.presentation.screen.filmdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kinopoisk.cinema.di.IoDispatcher
import kinopoisk.cinema.domain.repository.DetailFilmRepository
import kinopoisk.cinema.presentation.screen.filmdetail.model.FilmDetailUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO Это пиздец
class FilmDetailViewModel @Inject constructor(
    private val detailFilmRepository: DetailFilmRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<FilmDetailUiState>(FilmDetailUiState.Loading)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    fun getFilmDetail(id: Int) {
        viewModelScope.launch(ioDispatcher) {
            detailFilmRepository.getFilmDetail(id)
                .onSuccess { filmDetailModel ->
                    _uiStateFlow.emit(
                        FilmDetailUiState.DetailFilm(
                            filmDetailUiModel = FilmDetailUiModel(
                                detailFilm = filmDetailModel,
                            )
                        )
                    )
                    getStaff(id)
                    getGallery(id)
                    getSimilar(id)
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

    private fun getStaff(id: Int) {
        viewModelScope.launch(ioDispatcher) {
            detailFilmRepository.getStaff(id)
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

    private fun getGallery(id: Int) {
        viewModelScope.launch(ioDispatcher) {
            detailFilmRepository.getGallery(id)
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

    private fun getSimilar(id: Int) {
        viewModelScope.launch(ioDispatcher) {
            detailFilmRepository.getSimilar(id)
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
}
