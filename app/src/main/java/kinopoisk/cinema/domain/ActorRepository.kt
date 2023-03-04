package kinopoisk.cinema.domain

import kinopoisk.cinema.presentation.screen.actor.ActorModel

// TODO move to package "repository"
interface ActorRepository {

    suspend fun getActor(id: Int): Result<ActorModel>
}
