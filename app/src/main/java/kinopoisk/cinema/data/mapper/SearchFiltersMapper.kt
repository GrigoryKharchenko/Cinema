package kinopoisk.cinema.data.mapper

import kinopoisk.cinema.data.network.response.CountriesFilterResponse
import kinopoisk.cinema.data.network.response.GenresFilterResponse
import kinopoisk.cinema.data.network.response.SearchFilterResponse
import kinopoisk.cinema.extension.firstCharToUpperCase
import kinopoisk.cinema.presentation.screen.searchfilter.SearchFilterModel

fun SearchFilterResponse.mapToCountriesFilters(): List<SearchFilterModel> =
    this.countries.filter { it.country.isNotEmpty() }.map(CountriesFilterResponse::mapToCountryFilter)

fun SearchFilterResponse.mapToGenresFilters(): List<SearchFilterModel> =
    this.genres.filter { it.genre.isNotEmpty() }.map(GenresFilterResponse::mapToGenreFilter)

fun CountriesFilterResponse.mapToCountryFilter(): SearchFilterModel =
    SearchFilterModel(
        id = id,
        countryOrGenre = country
    )

fun GenresFilterResponse.mapToGenreFilter(): SearchFilterModel =
    SearchFilterModel(
        id = id,
        countryOrGenre = genre.firstCharToUpperCase()
    )
