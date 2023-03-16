package kinopoisk.cinema.data.repository

import kinopoisk.cinema.data.mapper.mapToStaffModel
import kinopoisk.cinema.data.network.KinopoiskApi
import kinopoisk.cinema.domain.repository.StaffRepository
import kinopoisk.cinema.presentation.screen.filmdetail.TypeStaff
import kinopoisk.cinema.presentation.screen.filmdetail.model.StaffModel
import javax.inject.Inject

class StaffRepositoryImpl @Inject constructor(
    private val kinopoiskApi: KinopoiskApi,
) : StaffRepository {

    override suspend fun getActors(filmId: Int): List<StaffModel> =
        getAllPersonal(filmId).filter { it.profession == TypeStaff.ACTOR }

    override suspend fun getStaff(filmId: Int): List<StaffModel> =
        getAllPersonal(filmId).filter { it.profession != TypeStaff.ACTOR }

    private suspend fun getAllPersonal(filmId: Int): List<StaffModel> =
        kinopoiskApi.getStaff(filmId).mapToStaffModel()
}
