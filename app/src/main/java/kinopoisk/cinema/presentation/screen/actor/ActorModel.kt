package kinopoisk.cinema.presentation.screen.actor

import kinopoisk.cinema.presentation.screen.actor.adapter.BestFilmsActorModel

data class ActorModel(
    val id: Int,
    val photo: String,
    val name: String,
    val profession: String,
    val countFilm: Int,
    val bestFilms: List<BestFilmsActorModel>
)
