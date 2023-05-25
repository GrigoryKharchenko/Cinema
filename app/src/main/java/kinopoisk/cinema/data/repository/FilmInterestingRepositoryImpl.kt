package kinopoisk.cinema.data.repository

import kinopoisk.cinema.data.dao.FilmInterestingDao
import kinopoisk.cinema.data.entity.FilmInterestingEntity
import kinopoisk.cinema.data.mapper.mapToFilmInteresting
import kinopoisk.cinema.domain.repository.FilmInterestingRepository
import kinopoisk.cinema.presentation.screen.homepage.TypeCardCategoryUiModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilmInterestingRepositoryImpl @Inject constructor(
    private val filmInterestingDao: FilmInterestingDao
) : FilmInterestingRepository {

    override fun subscribeToReceive(): Flow<List<FilmInterestingEntity>> = filmInterestingDao.subscribeToReceive()

    override suspend fun insertOrUpdate(typeCardViewedUiModel: TypeCardCategoryUiModel.FilmUiModel) =
        filmInterestingDao.insertOrUpdate(typeCardViewedUiModel.mapToFilmInteresting())

    override suspend fun deleteAllFilms() = filmInterestingDao.deleteAll()
}
