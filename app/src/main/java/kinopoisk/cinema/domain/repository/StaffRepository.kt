package kinopoisk.cinema.domain.repository

import kinopoisk.cinema.presentation.screen.filmdetail.model.StaffModel
import kinopoisk.cinema.presentation.screen.staff.TypeTitleStaff

interface StaffRepository {

    suspend fun getActors(filmId: Int): List<StaffModel>

    suspend fun getStaff(filmId: Int): List<StaffModel>
}
