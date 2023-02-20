package kinopoisk.cinema.domain

import kinopoisk.cinema.presentation.screen.actor.ActorModel

interface ActorRepository {

    suspend fun getActor(id: Int): Result<ActorModel>
}
