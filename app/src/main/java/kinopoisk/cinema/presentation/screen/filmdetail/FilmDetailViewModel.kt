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

    private fun updateUiState(updateUiModel: (oldUiModel: FilmDetailUiModel) -> FilmDetailUiModel) {
        _uiStateFlow.update { uiState ->
            when (uiState) {
                is FilmDetailUiState.DetailFilm ->
                    FilmDetailUiState.DetailFilm(updateUiModel(uiState.filmDetailUiModel))
                else ->
                    FilmDetailUiState.DetailFilm(updateUiModel(FilmDetailUiModel()))
            }
        }
    }

    private fun <T> getFilmInfo(
        request: suspend () -> Result<T>,
        onResult: (result: T?, isError: Boolean) -> Unit,
        doAfter: () -> Unit = {},
    ) {
        viewModelScope.launch(ioDispatcher) {
            request().onSuccess { result ->
                onResult(result, false)
                doAfter()
            }.onFailure {
                onResult(null, true)
            }
        }
    }

    private fun getFilmDetail() {
        getFilmInfo(request = {
            detailFilmRepository.getFilmDetailModel(filmId)
        }, onResult = { filmDetailModel, isError ->
            updateUiState { oldModel ->
                oldModel.copy(
                    detailFilm = filmDetailModel,
                    isVisibleTextError = isError,
                    isVisibleAppBar = !isError,
                    isVisibleNestedScroll = !isError
                )
            }
        }, doAfter = {
            getStaff()
            getGallery()
            getSimilar()
            getFilmViewed()
            getSeason()
        })
    }

    private fun getSeason() {
        getFilmInfo(request = {
            detailFilmRepository.getSerial(filmId)
        },
            onResult = { serial, isError ->
                updateUiState { oldModel ->
                    oldModel.copy(
                        countSeason = serial?.countSeasons ?: 0,
                        countEpisodes = serial?.seasons?.sumOf { seasonModel ->
                            seasonModel.episodes.count()
                        } ?: 0,
                        isSerial = serial?.seasons?.isNotEmpty() ?: !isError
                    )
                }
            })
    }

    private fun getStaff() {
        getFilmInfo(
            request = { detailFilmRepository.getStaffModel(filmId) },
            onResult = { staffModel, isError ->
                val actors = staffModel?.filter { it.profession == TypeStaff.ACTOR }
                val staff = staffModel?.filter { it.profession != TypeStaff.ACTOR }
                updateUiState { oldUiModel ->
                    oldUiModel.copy(
                        sizeActor = actors?.size.toString(),
                        sizeStuff = staff?.size.toString(),
                        staff = staff?.take(MAX_STAFF),
                        actor = actors?.take(MAX_ACTOR),
                        isVisibleActors = actors?.isNotEmpty() ?: !isError,
                        isVisibleStaff = staff?.isNotEmpty() ?: !isError,
                    )
                }
            })
    }

    private fun getGallery() {
        getFilmInfo(request = { detailFilmRepository.getGalleryModel(filmId) },
            onResult = { galleryModel, isError ->
                updateUiState { oldUiModel ->
                    oldUiModel.copy(
                        gallery = galleryModel,
                        sizeGallery = galleryModel?.size.toString(),
                        isVisibleGallery = galleryModel?.isNotEmpty() ?: !isError
                    )
                }
            })
    }

    private fun getSimilar() {
        getFilmInfo(request = { detailFilmRepository.getSimilarFilmModel(filmId) },
            onResult = { similar, isError ->
                updateUiState { oldUiModel ->
                    oldUiModel.copy(
                        similar = similar,
                        sizeSimilar = similar?.size.toString(),
                        isVisibleSimilar = similar?.isNotEmpty() ?: !isError,
                    )
                }
            })
    }

    private fun getFilmViewed() {
        viewModelScope.launch(ioDispatcher) {
            detailFilmRepository.getFilmViewedEntity(filmId)
                .onSuccess { filmViewedRepository.insertOrUpdate(it.mapToFilmViewedModel()) }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(filmId: Int): FilmDetailViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        private const val MAX_ACTOR = 20
        private const val MAX_STAFF = 6

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
