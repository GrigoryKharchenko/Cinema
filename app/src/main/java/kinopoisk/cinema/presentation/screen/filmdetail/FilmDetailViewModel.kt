package kinopoisk.cinema.presentation.screen.filmdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kinopoisk.cinema.di.IoDispatcher
import kinopoisk.cinema.domain.DetailFilmRepository
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

    fun getFilmDetail(id: Int) {
        getFilmInfo(request = {
            detailFilmRepository.getFilmDetail(id)
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
            getStaff(id)
            getGallery(id)
            getSimilar(id)
        })
    }

    private fun getStaff(id: Int) {
        getFilmInfo(request = {
            detailFilmRepository.getStaff(id)
        },
            onResult = { staff, isError ->
                updateUiState { oldModel ->
                    val actors = staff?.filter { it.profession == TypeStaff.ACTOR }
                    val filmmakers = staff?.filter { it.profession != TypeStaff.ACTOR }
                    oldModel.copy(
                        actor = actors,
                        staff = filmmakers,
                        isVisibleActors = !actors.isNullOrEmpty() && !isError,
                        isVisibleStaff = !filmmakers.isNullOrEmpty() && !isError,
                    )
                }
            })
    }

    private fun getGallery(id: Int) {
        getFilmInfo(request = {
            detailFilmRepository.getGallery(id)
        },
            onResult = { gallery, isError ->
                updateUiState { oldModel ->
                    oldModel.copy(
                        gallery = gallery,
                        isVisibleGallery = !gallery.isNullOrEmpty() && !isError
                    )
                }
            })
    }

    private fun getSimilar(id: Int) {
        getFilmInfo(request = {
            detailFilmRepository.getSimilar(id)
        },
            onResult = { similar, isError ->
                updateUiState { oldModel ->
                    oldModel.copy(
                        similar = similar,
                        isVisibleSimilar = !similar.isNullOrEmpty() && !isError
                    )
                }
            })
    }
}
