package kinopoisk.cinema.domain.reposittory

import androidx.paging.PagingSource
import kinopoisk.cinema.data.TypeCategories
import kinopoisk.cinema.presentation.screen.films.FilmModel

interface FilmsRepository {

    fun getAll(typeCategories: TypeCategories): PagingSource<Int, FilmModel>
}
