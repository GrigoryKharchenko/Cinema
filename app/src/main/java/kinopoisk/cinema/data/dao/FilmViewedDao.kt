package kinopoisk.cinema.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kinopoisk.cinema.data.entity.FilmViewedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmViewedDao {

    @Query("SELECT * FROM filmViewed")
    fun subscribeToReceive(): Flow<List<FilmViewedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(filmViewedEntity: FilmViewedEntity)

    @Query("DELETE FROM filmViewed")
    suspend fun deleteAll()

    @Query("DELETE FROM filmViewed WHERE id = :filmId")
    suspend fun deleteFilm(filmId: Int)
}
