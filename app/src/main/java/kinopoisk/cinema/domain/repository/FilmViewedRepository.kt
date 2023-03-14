package kinopoisk.cinema.domain.repository

import kinopoisk.cinema.data.entity.FilmViewedEntity
import kinopoisk.cinema.presentation.screen.homepage.TypeCardCategoryUiModel
import kotlinx.coroutines.flow.Flow

interface FilmViewedRepository {

    fun subscribeToReceive(): Flow<List<FilmViewedEntity>>
    suspend fun insertOrUpdate(typeCardViewedUiModel: TypeCardCategoryUiModel.FilmUiModel)
    suspend fun deleteAllFilms()
}
