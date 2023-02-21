package kinopoisk.cinema.data.mapper

import kinopoisk.cinema.data.network.response.ActorResponse
import kinopoisk.cinema.data.network.response.BestFilmsResponse
import kinopoisk.cinema.presentation.screen.actor.ActorModel
import kinopoisk.cinema.presentation.screen.actor.adapter.BestFilmsActorModel

private const val MAX_BEST_FILM = 10

fun ActorResponse.mapToActorModel(): ActorModel =
    ActorModel(
        id = id,
        photo = photo,
        name = nameRu ?: nameEn,
        profession = profession,
        countFilm = films.count(),
        bestFilms = mapToBestFilmsActor().sortedBy {
            it.rating
        }.asReversed().take(MAX_BEST_FILM)
    )

fun ActorResponse.mapToBestFilmsActor(): List<BestFilmsActorModel> =
    this.films.map(BestFilmsResponse::mapToBestFilmActorModel)

fun BestFilmsResponse.mapToBestFilmActorModel(): BestFilmsActorModel =
    BestFilmsActorModel(
        id = filmsId,
        nameFilm = nameFilmRu ?: nameFilmEn,
        rating = rating ?: "",
    )
