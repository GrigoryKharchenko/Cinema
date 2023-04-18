package kinopoisk.cinema.presentation.screen.filmdetail

import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kinopoisk.cinema.data.mapper.mapToFilmInteresting
import kinopoisk.cinema.data.mapper.mapToFilmViewedModel
import kinopoisk.cinema.di.IoDispatcher
import kinopoisk.cinema.domain.enumeration.ViewedState
import kinopoisk.cinema.domain.repository.DetailFilmRepository
import kinopoisk.cinema.domain.repository.FilmInterestingRepository
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
    private val filmViewedRepository: FilmViewedRepository,
    private val filmInterestingRepository: FilmInterestingRepository
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<FilmDetailUiState>(FilmDetailUiState.Loading)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    private var isViewed = INITIAL_IS_VIEWED
    private var isCollapsing = INITIAL_IS_COLLAPSED

    init {
        getFilmDetail()
    }

    fun checkViewedFilm(imageView: ImageView) {
        if (isViewed) {
            imageView.setImageResource(ViewedState.VIEWED.viewedState)
            insertFilmViewed()
        } else {
            imageView.setImageResource(ViewedState.DONT_VIEWED.viewedState)
            deleteFilm()
        }
        isViewed = !isViewed
    }

    fun checkCollapsed(textView: TextView) {
        if (isCollapsing) {
            textView.maxLines = Int.MAX_VALUE
        } else {
            textView.maxLines = MAX_LINE_COLLAPSED
        }
        isCollapsing = !isCollapsing
    }

    private fun getFilmDetail() {
        viewModelScope.launch(ioDispatcher) {
            detailFilmRepository.getFilmDetailModel(filmId)
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
                    insertFilmInteresting()
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
            detailFilmRepository.getStaffModel(filmId)
                .onSuccess { staffModel ->
                    val actors = staffModel.filter { it.profession == TypeStaff.ACTOR }
                    val staff = staffModel.filter { it.profession != TypeStaff.ACTOR }
                    _uiStateFlow.update { uiState ->
                        (uiState as? FilmDetailUiState.DetailFilm)?.copy(
                            filmDetailUiModel = uiState.filmDetailUiModel.copy(
                                sizeActor = actors.size.toString(),
                                sizeStuff = staffModel.size.toString(),
                                staff = staffModel.take(MAX_STAFF),
                                actor = actors.take(MAX_ACTOR),
                                isVisibleActors = actors.isNotEmpty(),
                                isVisibleStaff = staff.isNotEmpty(),
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
            detailFilmRepository.getGalleryModel(filmId)
                .onSuccess { gallery ->
                    _uiStateFlow.update { uiState ->
                        (uiState as? FilmDetailUiState.DetailFilm)?.copy(
                            filmDetailUiModel = uiState.filmDetailUiModel.copy(
                                gallery = gallery,
                                sizeGallery = gallery.size.toString(),
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
            detailFilmRepository.getSimilarFilmModel(filmId)
                .onSuccess { similar ->
                    _uiStateFlow.update { uiState ->
                        (uiState as? FilmDetailUiState.DetailFilm)?.copy(
                            filmDetailUiModel = uiState.filmDetailUiModel.copy(
                                similar = similar,
                                sizeSimilar = similar.size.toString(),
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

    private fun insertFilmViewed() {
        viewModelScope.launch(ioDispatcher) {
            detailFilmRepository.getFilmViewed(filmId)
                .onSuccess { filmViewedRepository.insertOrUpdate(it.mapToFilmViewedModel()) }
                .onFailure {
                    // Ignore
                }
        }
    }

    private fun insertFilmInteresting() {
        viewModelScope.launch(ioDispatcher) {
            detailFilmRepository.getFilmInteresting(filmId)
                .onSuccess { filmInterestingRepository.insertOrUpdate(it.mapToFilmInteresting()) }
                .onFailure {
                    // Ignore
                }
        }
    }

    private fun deleteFilm() {
        viewModelScope.launch(ioDispatcher) {
            filmViewedRepository.deleteFilm(filmId)
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
        private const val INITIAL_IS_VIEWED = true
        private const val INITIAL_IS_COLLAPSED = true
        private const val MAX_LINE_COLLAPSED = 5

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
