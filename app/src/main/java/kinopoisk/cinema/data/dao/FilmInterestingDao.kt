package kinopoisk.cinema.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kinopoisk.cinema.data.entity.FilmInterestingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmInterestingDao {

    @Query("SELECT * FROM filmInteresting")
    fun subscribeToReceive(): Flow<List<FilmInterestingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(filmInterestingEntity: FilmInterestingEntity)

    @Query("DELETE FROM filmInteresting")
    suspend fun deleteAll()
}
