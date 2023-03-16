package kinopoisk.cinema.domain.repository

import androidx.paging.PagingSource
import kinopoisk.cinema.presentation.screen.searchpage.SearchModel

interface SearchFilmRepository {

    operator fun invoke(nameFilm: String): PagingSource<Int, SearchModel>
}
