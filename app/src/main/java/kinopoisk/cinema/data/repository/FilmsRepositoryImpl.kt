package kinopoisk.cinema.data.repository

import androidx.paging.PagingSource
import kinopoisk.cinema.data.TypeCategories
import kinopoisk.cinema.data.network.FilmsPageSource
import kinopoisk.cinema.domain.FilmsRepository
import kinopoisk.cinema.presentation.screen.films.FilmModel
import javax.inject.Inject

class FilmsRepositoryImpl @Inject constructor(
    private val filmsPageSource: FilmsPageSource.Factory
) : FilmsRepository {

    override fun getAll(typeCategories: TypeCategories): PagingSource<Int, FilmModel> {
        return filmsPageSource.create(typeCategories)
    }
}
