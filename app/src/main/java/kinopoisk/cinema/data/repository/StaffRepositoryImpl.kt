package kinopoisk.cinema.data.repository

import kinopoisk.cinema.data.mapper.mapToStaffModel
import kinopoisk.cinema.data.network.KinopoiskApi
import kinopoisk.cinema.domain.repository.StaffRepository
import kinopoisk.cinema.presentation.screen.filmdetail.TypeStaff
import kinopoisk.cinema.presentation.screen.filmdetail.model.StaffModel
import kinopoisk.cinema.presentation.screen.staff.TypeTitleStaff
import javax.inject.Inject

class StaffRepositoryImpl @Inject constructor(
    private val kinopoiskApi: KinopoiskApi,
) : StaffRepository {

    override suspend fun getStuff(typeTitleStaff: TypeTitleStaff): List<StaffModel> {

        val response = kinopoiskApi.getStaff(typeTitleStaff.filmId).mapToStaffModel()

        return when (typeTitleStaff.typeStaff) {
            TypeStaff.ACTOR -> response.filter { it.profession == TypeStaff.ACTOR }
            else -> response.filter { it.profession != TypeStaff.ACTOR }
        }
    }
}
