package kinopoisk.cinema.di.module

import dagger.Binds
import dagger.Module
import kinopoisk.cinema.data.repository.CategoryRepositoryImpl
import kinopoisk.cinema.data.repository.DetailFilmRepositoryImpl
import kinopoisk.cinema.domain.CategoryRepository
import kinopoisk.cinema.domain.DetailFilmRepository

@Module
interface RepositoryModule {

    @Binds
    fun bindAllCategoryRepository(repositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    fun bindDetailFilmRepository(repositoryImpl: DetailFilmRepositoryImpl): DetailFilmRepository
}
