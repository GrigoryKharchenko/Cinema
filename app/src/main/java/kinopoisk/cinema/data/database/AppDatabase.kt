package kinopoisk.cinema.data.database

import kinopoisk.cinema.data.dao.FilmInterestingDao
import kinopoisk.cinema.data.dao.FilmViewedDao

interface AppDatabase {

    fun filmViewedDao(): FilmViewedDao

    fun filmInteresting(): FilmInterestingDao
}
