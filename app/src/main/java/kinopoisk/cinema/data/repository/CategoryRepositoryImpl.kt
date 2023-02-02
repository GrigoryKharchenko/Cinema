package kinopoisk.cinema.data.repository

import android.content.Context
import kinopoisk.cinema.R
import kinopoisk.cinema.data.network.FilmParameters.countries
import kinopoisk.cinema.data.network.FilmParameters.genres
import kinopoisk.cinema.data.TypeCategories
import kinopoisk.cinema.data.mapper.mapToDifferentFilmsModel
import kinopoisk.cinema.data.mapper.mapToTopFilmsModel
import kinopoisk.cinema.data.network.ApiConstants
import kinopoisk.cinema.data.network.FilmParameters.getRandomCountry
import kinopoisk.cinema.data.network.FilmParameters.getRandomGenre
import kinopoisk.cinema.data.network.KinopoiskApi
import kinopoisk.cinema.domain.CategoryRepository
import kinopoisk.cinema.presentation.screen.homepage.CategoryUiModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val kinopoiskApi: KinopoiskApi,
    private val context: Context,
) : CategoryRepository {

    override suspend fun getCategories(): List<CategoryUiModel> =
        coroutineScope {
            val (firstCountryName, firstCountryCode) = getRandomCountry()
            val (firstGenreName, firstGenreCode) = getRandomGenre()

            val (secondCountryName, secondCountryCode) = getRandomCountry()
            val (secondGenreName, secondGenreCode) = getRandomGenre()

            val premiers = async {
                kinopoiskApi.getPremieres().mapToDifferentFilmsModel()
            }
            val popular = async {
                kinopoiskApi.getTopFilms(type = ApiConstants.TOP_100).mapToTopFilmsModel()
            }
            val firstRandom = async {
                kinopoiskApi.getRandomCategory(
                    countries = firstCountryCode,
                    genres = firstGenreCode
                ).mapToDifferentFilmsModel(firstGenreName)
            }
            val top = async {
                kinopoiskApi.getTopFilms(type = ApiConstants.TOP_250).mapToTopFilmsModel()
            }
            val secondRandom = async {
                kinopoiskApi.getRandomCategory(
                    countries = secondCountryCode,
                    genres = secondGenreCode
                ).mapToDifferentFilmsModel(secondGenreName)
            }
            val serial = async {
                kinopoiskApi.getSerial().mapToDifferentFilmsModel()
            }

            listOf(
                CategoryUiModel(
                    typeCategory = TypeCategories.Premieres(
                        nameCategory = context.getString(R.string.home_premieres)
                    ),
                    films = premiers.await(),
                ),
                CategoryUiModel(
                    typeCategory = TypeCategories.Top(
                        nameCategory = context.getString(R.string.home_popular),
                        type = ApiConstants.TOP_100,
                    ),
                    films = popular.await(),
                ),
                CategoryUiModel(
                    typeCategory = TypeCategories.Random(
                        nameCategory = "$firstCountryName $firstGenreName",
                        countryCode = firstCountryCode,
                        genresCode = firstGenreCode
                    ),
                    films = firstRandom.await(),
                ),
                CategoryUiModel(
                    typeCategory = TypeCategories.Top(
                        nameCategory = context.getString(R.string.home_top),
                        type = ApiConstants.TOP_250,
                    ),
                    films = top.await(),
                ),
                CategoryUiModel(
                    typeCategory = TypeCategories.Random(
                        nameCategory = "$secondCountryName $secondGenreName",
                        countryCode = secondCountryCode,
                        genresCode = secondGenreCode
                    ),
                    films = secondRandom.await(),
                ),
                CategoryUiModel(
                    typeCategory = TypeCategories.Serials(
                        nameCategory = context.getString(R.string.home_serials),
                        type = ApiConstants.TV_SERIES,
                    ),
                    films = serial.await(),
                ),
            )
        }
}
