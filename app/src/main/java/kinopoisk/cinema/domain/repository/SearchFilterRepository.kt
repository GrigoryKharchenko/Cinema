package kinopoisk.cinema.domain.repository

import kinopoisk.cinema.presentation.screen.searchfilter.SearchFilterModel

interface SearchFilterRepository {

    suspend fun getCountry(): List<SearchFilterModel>

    suspend fun getGenre(): List<SearchFilterModel>
}
