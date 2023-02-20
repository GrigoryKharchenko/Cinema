package kinopoisk.cinema.di.module

import dagger.Binds
import dagger.Module
import kinopoisk.cinema.data.repository.ActorRepositoryImpl
import kinopoisk.cinema.data.repository.CategoryRepositoryImpl
import kinopoisk.cinema.data.repository.DetailFilmRepositoryImpl
import kinopoisk.cinema.data.repository.FilmsRepositoryImpl
import kinopoisk.cinema.domain.ActorRepository
import kinopoisk.cinema.domain.CategoryRepository
import kinopoisk.cinema.domain.DetailFilmRepository
import kinopoisk.cinema.domain.FilmsRepository

@Module
interface RepositoryModule {

    @Binds
    fun bindAllCategoryRepository(repositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    fun bindDetailFilmRepository(repositoryImpl: DetailFilmRepositoryImpl): DetailFilmRepository

    @Binds
    fun bindFilmsPageSourceRepository(repositoryImpl: FilmsRepositoryImpl): FilmsRepository

    @Binds
    fun bindActorRepository(repositoryImpl: ActorRepositoryImpl): ActorRepository
}
