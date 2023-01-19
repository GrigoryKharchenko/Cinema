package kinopoisk.cinema.data.repository

import kinopoisk.cinema.R
import kinopoisk.cinema.data.mapper.mapToDifferentFilmsModel
import kinopoisk.cinema.data.mapper.mapToTopFilmsModel
import kinopoisk.cinema.data.network.ApiConstants
import kinopoisk.cinema.data.network.KinopoiskApi
import kinopoisk.cinema.domain.CategoryRepository
import kinopoisk.cinema.presentation.screen.homepage.CategoryUiModel
import kinopoisk.cinema.presentation.screen.homepage.TypeCardCategoryUiModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.util.Random
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val kinopoiskApi: KinopoiskApi,
) : CategoryRepository {

    override suspend fun getCategories(): List<CategoryUiModel> =
        coroutineScope {
            // TODO: а зачем тут Random четыре раза создается?
            val firstRandomCountry = (Random().nextInt(ApiConstants.MAX_COUNTRIES) + 1).toString()
            val firstRandomGenre = (Random().nextInt(ApiConstants.MAX_GENRES) + 1).toString()
            val secondRandomCountry = (Random().nextInt(ApiConstants.MAX_COUNTRIES) + 1).toString()
            val secondRandomGenre = (Random().nextInt(ApiConstants.MAX_GENRES) + 1).toString()
            val premiers = async {
                kinopoiskApi.getPremieres().mapToDifferentFilmsModel().plus(TypeCardCategoryUiModel.FooterUiModel())
            }
            val popular = async {
                kinopoiskApi.getPopularFilms().mapToTopFilmsModel().plus(TypeCardCategoryUiModel.FooterUiModel())
            }
            val firstRandom = async {
                kinopoiskApi.getFirstRandomFilms(
                    countries = firstRandomCountry,
                    genres = firstRandomGenre
                ).mapToDifferentFilmsModel().plus(TypeCardCategoryUiModel.FooterUiModel())
            }
            val top = async {
                kinopoiskApi.getTopFilms().mapToTopFilmsModel().plus(TypeCardCategoryUiModel.FooterUiModel())
            }
            val secondRandom = async {
                kinopoiskApi.getSecondRandomFilms(
                    countries = secondRandomCountry,
                    genres = secondRandomGenre
                ).mapToDifferentFilmsModel().plus(TypeCardCategoryUiModel.FooterUiModel())
            }
            val serial = async {
                // TODO: plus(TypeCardCategoryUiModel.FooterUiModel()) абсолютно во всех запросах. может куда-то вынести чтоб не копировать?
                kinopoiskApi.getSerial().mapToDifferentFilmsModel().plus(TypeCardCategoryUiModel.FooterUiModel())
            }

            listOf(
                CategoryUiModel(
                    category = R.string.home_premieres,
                    films = premiers.await(),
                ),
                CategoryUiModel(
                    category = R.string.home_popular,
                    films = popular.await(),
                ),
                CategoryUiModel(
                    category = R.string.home_random,
                    films = firstRandom.await(),
                ),
                CategoryUiModel(
                    category = R.string.home_top,
                    films = top.await(),
                ),
                CategoryUiModel(
                    category = R.string.home_random,
                    films = secondRandom.await(),
                ),
                CategoryUiModel(
                    category = R.string.home_serials,
                    films = serial.await(),
                ),
            )
        }
}
