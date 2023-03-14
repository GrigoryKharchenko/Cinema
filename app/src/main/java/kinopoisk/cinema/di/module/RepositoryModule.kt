package kinopoisk.cinema.di.module

import dagger.Binds
import dagger.Module
import kinopoisk.cinema.data.repository.ActorRepositoryImpl
import kinopoisk.cinema.data.repository.CategoryRepositoryImpl
import kinopoisk.cinema.data.repository.DetailFilmRepositoryImpl
import kinopoisk.cinema.data.repository.FilmViewedViewedRepositoryImpl
import kinopoisk.cinema.data.repository.FilmsRepositoryImpl
import kinopoisk.cinema.domain.repository.ActorRepository
import kinopoisk.cinema.domain.repository.CategoryRepository
import kinopoisk.cinema.domain.repository.DetailFilmRepository
import kinopoisk.cinema.domain.repository.FilmViewedRepository
import kinopoisk.cinema.domain.repository.FilmsRepository

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

    @Binds
    fun bindFilmRepository(repositoryImpl: FilmViewedViewedRepositoryImpl): FilmViewedRepository
}
