package kinopoisk.cinema.domain.usecase

import androidx.paging.PagingSource
import kinopoisk.cinema.domain.repository.SearchFilmRepository
import kinopoisk.cinema.presentation.screen.searchpage.SearchModel
import javax.inject.Inject

class QueryFilmUseCase @Inject constructor(
    private val searchFilmRepository: SearchFilmRepository
) {

    operator fun invoke(query: String): PagingSource<Int, SearchModel> =
        searchFilmRepository.getAll(query)
}
