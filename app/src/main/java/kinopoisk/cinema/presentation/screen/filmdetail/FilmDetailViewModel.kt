package kinopoisk.cinema.presentation.screen.filmdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kinopoisk.cinema.di.IoDispatcher
import kinopoisk.cinema.domain.DetailFilmRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmDetailViewModel @Inject constructor(
    private val detailFilmRepository: DetailFilmRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<FilmDetailUiState>(FilmDetailUiState.Loading)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    fun getFilmDetail(id: Int) {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                detailFilmRepository.getFilmDetail(id)
            }.onSuccess { filmDetailUiModel ->
                _uiStateFlow.emit(
                    FilmDetailUiState.Success(
                        detailFilm = filmDetailUiModel,
                        actor = detailFilmRepository.getActor(id).filter { filmCrew ->
                            filmCrew.profession == TypeFilmCrew.ACTOR.name
                        },
                        gallery = detailFilmRepository.getGallery(id),
                        similar = detailFilmRepository.getSimilar(id),
                        filmCrew = detailFilmRepository.getFilmCrew(id).filter { filmCrew ->
                            filmCrew.profession != TypeFilmCrew.ACTOR.name
                        }
                    )
                )
            }.onFailure {
                _uiStateFlow.emit(FilmDetailUiState.Error)
            }
        }
    }
}
