package kinopoisk.cinema.data.mapper

import kinopoisk.cinema.data.network.response.CountriesFilterResponse
import kinopoisk.cinema.data.network.response.GenresFilterResponse
import kinopoisk.cinema.data.network.response.SearchFilterResponse
import kinopoisk.cinema.extension.firstCharToUpperCase
import kinopoisk.cinema.presentation.screen.searchfilter.SearchFilterModel

fun SearchFilterResponse.mapToCountriesFilters(): List<SearchFilterModel> =
    this.countries.map(CountriesFilterResponse::mapToCountryFilter).filter {
        it.countryOrGenre.isNotEmpty()
    }

fun SearchFilterResponse.mapToGenresFilters(): List<SearchFilterModel> =
    this.genres.map(GenresFilterResponse::mapToGenreFilter).filter {
        it.countryOrGenre.isNotEmpty()
    }

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
