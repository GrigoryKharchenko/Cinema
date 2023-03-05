package kinopoisk.cinema.data.mapper

import kinopoisk.cinema.data.network.response.CountryResponse
import kinopoisk.cinema.data.network.response.DetailFilmResponse
import kinopoisk.cinema.data.network.response.GenreResponse
import kinopoisk.cinema.presentation.screen.filmdetail.model.FilmDetailModel
import org.junit.Test
import org.junit.jupiter.api.Assertions

class FilmDetailMapper {


    private val detailFilm = "rating nameFilm\nreleased, Genre\ncountry, duration мин, rating"

    private val detailFilmResponse = DetailFilmResponse(
        id = 1,
        poster = "poster",
        logo = "logo",
        rating = "rating",
        nameRu = "nameFilm",
        nameEn = "nameFilm",
        nameOriginal = "nameFilm",
        released = "released",
        genres = listOf(GenreResponse(genre = "genre")),
        countries = listOf(CountryResponse("country")),
        duration = "duration",
        ratingMpaa = "rating",
        ratingAgeLimits = "rating",
        shortDescription = "shortDescription",
        description = "description"
    )

    private val filmDetailModel = FilmDetailModel(
        id = 1,
        poster = "poster",
        logo = "logo",
        name = "nameFilm",
        detailFilm = detailFilm,
        shortDescription = "shortDescription",
        description = "description",
        isVisibleShortDescription = true
    )

    @Test
    fun `when mapToString- then success`() {
        //given
        val from = detailFilmResponse
        val to = detailFilm

        //when
        val result = from.mapToDetailFilm()

        //then
        Assertions.assertEquals(to, result)
    }


    @Test
    fun `when mapToDetailFilm - then success`() {
        //given
        val from = detailFilmResponse
        val to = filmDetailModel

        //when
        val result = from.mapToDetailFilmModel()

        //then
        Assertions.assertEquals(to, result)
    }
}
