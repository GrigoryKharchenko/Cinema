package kinopoisk.cinema.data.repository

import kinopoisk.cinema.data.dao.FilmViewedDao
import kinopoisk.cinema.data.entity.FilmViewedEntity
import kinopoisk.cinema.data.mapper.mapToFilmViewedEntity
import kinopoisk.cinema.domain.repository.FilmViewedRepository
import kinopoisk.cinema.presentation.screen.homepage.TypeCardCategoryUiModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilmViewedViewedRepositoryImpl @Inject constructor(
    private val filmViewedDao: FilmViewedDao,
) : FilmViewedRepository {

    override fun subscribeToReceive(): Flow<List<FilmViewedEntity>> = filmViewedDao.subscribeToReceive()

    override suspend fun insertOrUpdate(typeCardViewedUiModel: TypeCardCategoryUiModel.FilmUiModel) =
        filmViewedDao.insertOrUpdate(typeCardViewedUiModel.mapToFilmViewedEntity())

    override suspend fun deleteAllFilms() = filmViewedDao.deleteALl()
}
