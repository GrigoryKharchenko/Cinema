package kinopoisk.cinema.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kinopoisk.cinema.data.entity.FilmViewedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmViewedDao {

    @Query("SELECT * FROM film")
    fun subscribeToReceive(): Flow<List<FilmViewedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilm(filmViewedEntity: FilmViewedEntity)

    @Query("DELETE FROM film")
    suspend fun deleteALlFilm()
}
