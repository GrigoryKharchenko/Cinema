package kinopoisk.cinema.presentation.screen.actor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kinopoisk.cinema.di.IoDispatcher
import kinopoisk.cinema.domain.repository.ActorRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ActorViewModel @AssistedInject constructor(
    private val actorRepository: ActorRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @Assisted private val actorId: Int
) : ViewModel() {

    private val _actorFlow = MutableStateFlow<ActorUiState>(ActorUiState.Loading)
    val actorFlow = _actorFlow.asStateFlow()

    init {
        getActors()
    }

    private fun getActors() {
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

    @AssistedFactory
    interface Factory {
        fun create(actorId: Int): ActorViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            actorId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(actorId) as T
            }
        }
    }
}
