package kinopoisk.cinema.data.mapper

import kinopoisk.cinema.data.network.response.ActorResponse
import kinopoisk.cinema.data.network.response.BestFilmsActorResponse
import kinopoisk.cinema.presentation.screen.actor.ActorModel
import kinopoisk.cinema.presentation.screen.actor.BestFilmsActorModel
import org.junit.Test
import org.junit.jupiter.api.Assertions

class ActorMapperTest {

    private val actorResponse = ActorResponse(
        id = 1,
        photo = "photo",
        nameRu = "nameRu",
        nameEn = "nameEn",
        profession = "profession",
        films = listOf(
            BestFilmsActorResponse(filmsId = 1, nameFilmRu = "nameFilm1", nameFilmEn = "nameFilm1", rating = "rating1"),
            BestFilmsActorResponse(filmsId = 2, nameFilmRu = null, nameFilmEn = "nameFilm2", rating = "rating2")
        )
    )

    private val actorModel = ActorModel(
        id = 1,
        photo = "photo",
        name = "nameRu",
        profession = "profession",
        countFilm = 2,
        bestFilms = listOf(
            BestFilmsActorModel(id = 1, nameFilm = "nameFilm1", rating = "rating1"),
            BestFilmsActorModel(id = 2, nameFilm = "nameFilm2", rating = "rating2")
        ).reversed()
    )

    @Test
    fun `when map - then success`() {
        //given
        val from = actorResponse
        val to = actorModel

        //when
        val result = from.mapToActorModel()

        //then
        Assertions.assertEquals(to,result)
    }
}
