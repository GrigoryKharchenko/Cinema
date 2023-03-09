package kinopoisk.cinema.data.mapper

import kinopoisk.cinema.data.network.response.SearchFilmResponse
import kinopoisk.cinema.data.network.response.SearchFilmsResponse
import kinopoisk.cinema.presentation.screen.searchpage.SearchModel

fun SearchFilmsResponse.mapToSearchFilmsModel(): List<SearchModel> =
    this.anotherFilms.map(SearchFilmResponse::mapToSearchFilmModel)

fun SearchFilmResponse.mapToSearchFilmModel(): SearchModel =
    SearchModel(
        id = id,
        poster = poster,
        nameFilm = nameRu ?: nameEn ?: nameOriginal,
        rating = ratingKinopoisk ?: ratingImdb,
        yearAndGenre = mapToDataAndGenre()
    )

private fun SearchFilmResponse.mapToDataAndGenre(): String {
    return "$year,${genres.first().genre}"
}
