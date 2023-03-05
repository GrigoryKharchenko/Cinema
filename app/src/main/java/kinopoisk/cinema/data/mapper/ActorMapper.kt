package kinopoisk.cinema.data.mapper

import kinopoisk.cinema.data.network.response.ActorResponse
import kinopoisk.cinema.data.network.response.BestFilmsActorResponse
import kinopoisk.cinema.presentation.screen.actor.ActorModel
import kinopoisk.cinema.presentation.screen.actor.BestFilmsActorModel

private const val MAX_BEST_FILM = 10

fun ActorResponse.mapToActorModel(): ActorModel =
    ActorModel(
        id = id,
        photo = photo,
        name = nameRu ?: nameEn,
        profession = profession,
        countFilm = films.count(),
        bestFilms = mapToBestFilmsActorModel().sortedBy {
            it.rating
        }.asReversed().take(MAX_BEST_FILM)
    )

fun ActorResponse.mapToBestFilmsActorModel(): List<BestFilmsActorModel> =
    this.films.map(BestFilmsActorResponse::mapToBestFilmActorModel)

fun BestFilmsActorResponse.mapToBestFilmActorModel(): BestFilmsActorModel =
    BestFilmsActorModel(
        id = filmsId,
        nameFilm = nameFilmRu ?: nameFilmEn,
        rating = rating ?: "",
    )
