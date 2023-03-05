package kinopoisk.cinema.data.repository

import kinopoisk.cinema.data.mapper.mapToActorModel
import kinopoisk.cinema.data.network.KinopoiskApi
import kinopoisk.cinema.domain.repository.ActorRepository
import kinopoisk.cinema.presentation.screen.actor.ActorModel
import javax.inject.Inject

class ActorRepositoryImpl @Inject constructor(
    private val kinopoiskApi: KinopoiskApi
) : ActorRepository {

    override suspend fun getActor(id: Int): Result<ActorModel> =
        runCatching {
            kinopoiskApi.getActor(id).mapToActorModel()
        }
}
