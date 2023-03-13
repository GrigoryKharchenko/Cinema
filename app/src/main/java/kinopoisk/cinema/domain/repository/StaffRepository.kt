package kinopoisk.cinema.domain.repository

import kinopoisk.cinema.presentation.screen.filmdetail.model.StaffModel
import kinopoisk.cinema.presentation.screen.staff.TypeTitleStaff

interface StaffRepository {

    suspend fun getStuff(typeTitleStaff: TypeTitleStaff): List<StaffModel>
}
