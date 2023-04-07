package kinopoisk.cinema.domain.repository

import kinopoisk.cinema.data.entity.FilmInterestingEntity
import kinopoisk.cinema.presentation.screen.homepage.TypeCardCategoryUiModel
import kotlinx.coroutines.flow.Flow

interface FilmInterestingRepository {

    fun subscribeToReceive(): Flow<List<FilmInterestingEntity>>

    suspend fun insertOrUpdate(typeCardViewedUiModel: TypeCardCategoryUiModel.FilmUiModel)

    suspend fun deleteAllFilms()
}
