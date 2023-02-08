package kinopoisk.cinema.di.module

import dagger.Binds
import dagger.Module
import kinopoisk.cinema.data.repository.CategoryRepositoryImpl
import kinopoisk.cinema.data.repository.DetailFilmRepositoryImpl
import kinopoisk.cinema.data.repository.FilmsRepositoryImpl
import kinopoisk.cinema.domain.reposittory.CategoryRepository
import kinopoisk.cinema.domain.reposittory.DetailFilmRepository
import kinopoisk.cinema.domain.reposittory.FilmsRepository

@Module
interface RepositoryModule {

    @Binds
    fun bindAllCategoryRepository(repositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    fun bindDetailFilmRepository(repositoryImpl: DetailFilmRepositoryImpl): DetailFilmRepository

    @Binds
    fun bindFilmsPageSourceRepository(repositoryImpl: FilmsRepositoryImpl): FilmsRepository
}
