package kinopoisk.cinema.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import kinopoisk.cinema.data.database.AppDatabase
import kinopoisk.cinema.data.database.AppRoomDatabase
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase = AppRoomDatabase.buildDatabase(context)

    @Provides
    fun provideFilmDao(appDatabase: AppDatabase) = appDatabase.filmViewedDao()

    @Provides
    fun provideFilmInterestingDao(appDatabase: AppDatabase) = appDatabase.filmInteresting()
}
