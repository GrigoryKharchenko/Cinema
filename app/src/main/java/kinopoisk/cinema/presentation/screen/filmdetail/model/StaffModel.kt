package kinopoisk.cinema.presentation.screen.filmdetail.model

import kinopoisk.cinema.presentation.screen.filmdetail.TypeStaff

data class StaffModel(
    val id: Int,
    val name: String?,
    val photo: String,
    val character: String?,
    val profession: TypeStaff,
)
