package kinopoisk.cinema.presentation.screen.actor

sealed interface ActorUiState {

    object Loading : ActorUiState
    object Error : ActorUiState
    data class Success(
        val actorModel: ActorModel
    ) : ActorUiState
}
