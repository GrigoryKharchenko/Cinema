package kinopoisk.cinema.data.mapper

import kinopoisk.cinema.data.network.response.CountryResponse
import kinopoisk.cinema.data.network.response.FilmResponse
import kinopoisk.cinema.data.network.response.FilmsResponse
import kinopoisk.cinema.data.network.response.GenreResponse
import kinopoisk.cinema.presentation.screen.films.FilmModel
import kinopoisk.cinema.presentation.screen.homepage.TypeCardCategoryUiModel
import org.junit.Test
import org.junit.jupiter.api.Assertions

class FilmsMapperTest {

    private val filmsResponse = FilmsResponse(
        anotherFilms = listOf(
            FilmResponse(
                nameRu = "nameFilm",
                nameEn = "nameFilm",
                nameOriginal = "nameFilm",
                rating = "rating",
                ratingKinopoisk = "rating",
                poster = "poster",
                dateRelease = "date",
                genres = listOf(
                    GenreResponse(genre = "genre"),
                    GenreResponse(genre = "genre")
                ),
                kinopoiskId = 1,
                id = 1,
                countries = listOf(
                    CountryResponse(country = "country"),
                    CountryResponse(country = "country")
                )
            )
        ),
        topFilms = listOf(
            FilmResponse(
                nameRu = "nameFilm",
                nameEn = "nameFilm",
                nameOriginal = "nameFilm",
                rating = "rating",
                ratingKinopoisk = "rating",
                poster = "poster",
                dateRelease = "date",
                genres = listOf(
                    GenreResponse(genre = "genre"),
                    GenreResponse(genre = "genre")
                ),
                kinopoiskId = 1,
                id = 1,
                countries = listOf(
                    CountryResponse(country = "country"),
                    CountryResponse(country = "country")
                )
            )
        )
    )

    private val filmsModel = listOf(
        FilmModel(
            id = 1,
            poster = "poster",
            rating = "rating",
            isViewed = false,
            name = "nameFilm",
            genre = "Genre",
            isVisibleRating = true
        )
    )

    private val filmResponse = FilmResponse(
        nameRu = "nameFilm",
        nameEn = "nameFilm",
        nameOriginal = "nameFilm",
        rating = "rating",
        ratingKinopoisk = "rating",
        poster = "poster",
        dateRelease = "date",
        genres = listOf(
            GenreResponse(genre = "genre"),
            GenreResponse(genre = "genre")
        ),
        kinopoiskId = 1,
        id = 1,
        countries = listOf(
            CountryResponse(country = "country"),
            CountryResponse(country = "country")
        )
    )

    private val typeCard = TypeCardCategoryUiModel.FilmUiModel(
        id = 1,
        poster = "poster",
        rating = "rating",
        isViewed = false,
        name = "nameFilm",
        genre = "Genre",
        isVisibleRating = true
    )

    @Test
    fun `when map - then success`() {
        //given
        val from = filmsResponse
        val to = filmsModel

        //when
        val result1 = from.mapToAllFilmsModel()
        val result2 = from.mapToAllAnotherFilmsModel()

        //then
        Assertions.assertEquals(to, result1)
        Assertions.assertEquals(to, result2)
    }

    @Test
    fun `when map type - then success`() {
        //given
        val from = filmResponse
        val to = typeCard

        //when
        val result = from.mapToFilmModel()

        Assertions.assertEquals(to, result)
    }
}
