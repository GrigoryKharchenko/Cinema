package kinopoisk.cinema.di.module

import dagger.Binds
import dagger.Module
import kinopoisk.cinema.data.repository.ActorRepositoryImpl
import kinopoisk.cinema.data.repository.CategoryRepositoryImpl
import kinopoisk.cinema.data.repository.DetailFilmRepositoryImpl
import kinopoisk.cinema.data.repository.FilmInterestingRepositoryImpl
import kinopoisk.cinema.data.repository.FilmViewedViewedRepositoryImpl
import kinopoisk.cinema.data.repository.FilmsRepositoryImpl
import kinopoisk.cinema.data.repository.GalleryRepositoryImpl
import kinopoisk.cinema.data.repository.StaffRepositoryImpl
import kinopoisk.cinema.data.repository.SearchFilmRepositoryImpl
import kinopoisk.cinema.data.repository.SearchFilterRepositoryImpl
import kinopoisk.cinema.domain.repository.ActorRepository
import kinopoisk.cinema.domain.repository.CategoryRepository
import kinopoisk.cinema.domain.repository.DetailFilmRepository
import kinopoisk.cinema.domain.repository.FilmInterestingRepository
import kinopoisk.cinema.domain.repository.FilmViewedRepository
import kinopoisk.cinema.domain.repository.FilmsRepository
import kinopoisk.cinema.domain.repository.GalleryRepository
import kinopoisk.cinema.domain.repository.StaffRepository
import kinopoisk.cinema.domain.repository.SearchFilmRepository
import kinopoisk.cinema.domain.repository.SearchFilterRepository

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
    fun bindGalleryRepository(repositoryImpl: GalleryRepositoryImpl): GalleryRepository

    @Binds
    fun bindStaffRepository(repositoryImpl: StaffRepositoryImpl): StaffRepository

    @Binds
    fun bindFilmRepository(repositoryImpl: FilmViewedViewedRepositoryImpl): FilmViewedRepository

    @Binds
    fun bindSearchFilmRepository(repositoryImpl: SearchFilmRepositoryImpl): SearchFilmRepository

    @Binds
    fun bindSearchFilterRepository(repositoryImpl: SearchFilterRepositoryImpl): SearchFilterRepository

    @Binds
    fun bindFilmInterestingRepository(repositoryImpl: FilmInterestingRepositoryImpl): FilmInterestingRepository
}
