package kinopoisk.cinema.data.repository

import androidx.paging.PagingSource
import kinopoisk.cinema.data.network.SearchFilmsPageSource
import kinopoisk.cinema.domain.repository.SearchFilmRepository
import kinopoisk.cinema.presentation.screen.searchpage.SearchModel
import javax.inject.Inject

class SearchFilmRepositoryImpl @Inject constructor(
    private val searchFilmsPageSource: SearchFilmsPageSource.Factory
) : SearchFilmRepository {

    override fun getSearchFilm(nameFilm: String): PagingSource<Int, SearchModel> =
        searchFilmsPageSource.create(nameFilm)
}
