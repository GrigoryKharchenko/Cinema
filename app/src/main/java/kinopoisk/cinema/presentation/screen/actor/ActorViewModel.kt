package kinopoisk.cinema.presentation.screen.actor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kinopoisk.cinema.di.IoDispatcher
import kinopoisk.cinema.domain.ActorRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ActorViewModel @Inject constructor(
    private val actorRepository: ActorRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _actorFlow = MutableStateFlow<ActorUiState>(ActorUiState.Loading)
    val actorFlow = _actorFlow.asStateFlow()

    fun getActors(actorId: Int) {
        viewModelScope.launch(ioDispatcher) {
            actorRepository.getActor(actorId)
                .onSuccess { actorModel ->
                    _actorFlow.emit(
                        ActorUiState.Success(
                            actorModel = ActorModel(
                                id = actorModel.id,
                                photo = actorModel.photo,
                                name = actorModel.name,
                                profession = actorModel.profession,
                                countFilm = actorModel.countFilm,
                                bestFilms = actorModel.bestFilms
                            )
                        )
                    )
                }.onFailure {
                    _actorFlow.emit(ActorUiState.Error)
                }
        }
    }
}
