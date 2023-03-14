package kinopoisk.cinema.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kinopoisk.cinema.BuildConfig
import kinopoisk.cinema.data.entity.FilmViewedEntity

@Database(
    entities = [FilmViewedEntity::class],
    exportSchema = false,
    version = BuildConfig.DB_VERSION
)
abstract class AppRoomDatabase : RoomDatabase(), AppDatabase {

    companion object {
        private const val DATABASE_NAME = "database.db"

        fun buildDatabase(context: Context): AppRoomDatabase =
            Room.databaseBuilder(context, AppRoomDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}
