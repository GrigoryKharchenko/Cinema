package kinopoisk.cinema.data.repository

import android.content.Context
import kinopoisk.cinema.R
import kinopoisk.cinema.data.TypeCategories
import kinopoisk.cinema.data.mapper.mapToDifferentFilmsModel
import kinopoisk.cinema.data.mapper.mapToTopFilmsModel
import kinopoisk.cinema.data.network.ApiConstants
import kinopoisk.cinema.data.network.KinopoiskApi
import kinopoisk.cinema.domain.CategoryRepository
import kinopoisk.cinema.presentation.screen.homepage.CategoryUiModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

private val countries = listOf("США", "Швейцария", "Франция")
private val genres = listOf("Триллер", "Драма", "Криминал")

class CategoryRepositoryImpl @Inject constructor(
    private val kinopoiskApi: KinopoiskApi,
    private val context: Context,
) : CategoryRepository {

    override suspend fun getCategories(): List<CategoryUiModel> =
        coroutineScope {
            val (firstCountryIndex, firstCountryCode) = getRandomCountry()
            val (firstGenreIndex, firstGenreCode) = getRandomGenre()

            val (secondCountryIndex, secondCountryCode) = getRandomCountry()
            val (secondGenreIndex, secondGenreCode) = getRandomGenre()

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
                ).mapToDifferentFilmsModel(genres[firstGenreIndex])
            }
            val top = async {
                kinopoiskApi.getTopFilms(type = ApiConstants.TOP_250).mapToTopFilmsModel()
            }
            val secondRandom = async {
                kinopoiskApi.getRandomCategory(
                    countries = secondCountryCode,
                    genres = secondGenreCode
                ).mapToDifferentFilmsModel(genres[secondGenreIndex])
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
                        nameCategory = "${countries[firstCountryIndex]} ${genres[firstGenreIndex]}",
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
                        nameCategory = "${countries[secondCountryIndex]} ${genres[secondGenreIndex]}",
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

    private fun getRandomCountry(): Pair<Int, String> {
        val countryIndex = countries.indices.random()
        val countryCode = (countryIndex + 1).toString()
        return Pair(countryIndex, countryCode)
    }

    private fun getRandomGenre(): Pair<Int, String> {
        val genreIndex = genres.indices.random()
        val genreCode = (genreIndex + 1).toString()
        return Pair(genreIndex, genreCode)
    }
}
