package kinopoisk.cinema.presentation.screen.season

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kinopoisk.cinema.data.mapper.mapToSeason
import kinopoisk.cinema.di.IoDispatcher
import kinopoisk.cinema.domain.repository.DetailFilmRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SeasonViewModel @AssistedInject constructor(
    private val detailFilmRepository: DetailFilmRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @Assisted private val serialInfo: SerialInfo
) : ViewModel() {

    private val _uiState = MutableStateFlow<SeasonUiState>(SeasonUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var serialModel: SerialModel? = null

    init {
        getFirstSeason()
    }

    private fun getFirstSeason() {
        viewModelScope.launch(ioDispatcher) {
            detailFilmRepository.getSerial(serialInfo.filmId)
                .onSuccess { serialModelResponse ->
                    serialModel = serialModelResponse
                    serialModelResponse.mapToSeason(FIRST_SEASON, _uiState, true)
                }
                .onFailure {
                    _uiState.emit(SeasonUiState.Error)
                }
        }
    }

    fun getCurrentSeason(numberSeason: String) {
        val safeSerial = requireNotNull(serialModel)
        viewModelScope.launch {
            safeSerial.mapToSeason(numberSeason, _uiState, false)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(serialInfo: SerialInfo): SeasonViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        private const val FIRST_SEASON = "1"
        fun provideFactory(
            assistedFactory: Factory,
            serialInfo: SerialInfo
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(serialInfo) as T
            }
        }
    }
}
