package kinopoisk.cinema.data.repository

import kinopoisk.cinema.data.mapper.mapToCountriesFilters
import kinopoisk.cinema.data.mapper.mapToGenresFilters
import kinopoisk.cinema.data.network.KinopoiskApi
import kinopoisk.cinema.domain.repository.SearchFilterRepository
import kinopoisk.cinema.presentation.screen.searchfilter.SearchFilterModel
import javax.inject.Inject

class SearchFilterRepositoryImpl @Inject constructor(
    private val kinopoiskApi: KinopoiskApi,
) : SearchFilterRepository {

    override suspend fun getCountry(): List<SearchFilterModel> =
        kinopoiskApi.getFilters().mapToCountriesFilters()

    override suspend fun getGenre(): List<SearchFilterModel> =
        kinopoiskApi.getFilters().mapToGenresFilters()
}
