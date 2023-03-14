package kinopoisk.cinema.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "film")
data class FilmViewedEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "poster")
    val poster: String,
    @ColumnInfo(name = "rating")
    val rating: String,
    @ColumnInfo(name = "isViewed")
    val isViewed: Boolean,
    @ColumnInfo(name = "nameFilm")
    val nameFilm: String,
    @ColumnInfo(name = "genre")
    val genre: String,
    @ColumnInfo(name = "isVisibleRating")
    val isVisibleRating: Boolean
)
