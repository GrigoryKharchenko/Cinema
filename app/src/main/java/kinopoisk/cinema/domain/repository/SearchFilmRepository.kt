package kinopoisk.cinema.domain.repository

import androidx.paging.PagingSource
import kinopoisk.cinema.presentation.screen.searchpage.SearchModel

interface SearchFilmRepository {

    fun getSearchFilm(nameFilm: String): PagingSource<Int, SearchModel>
}
