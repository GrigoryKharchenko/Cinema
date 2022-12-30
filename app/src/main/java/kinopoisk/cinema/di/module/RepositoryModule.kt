package kinopoisk.cinema.di.module

import dagger.Binds
import dagger.Module
import kinopoisk.cinema.data.repository.CategoryRepositoryImpl
import kinopoisk.cinema.domain.CategoryRepository

@Module
interface RepositoryModule {

    @Binds
    fun bindAllCategoryRepository(repositoryImpl: CategoryRepositoryImpl): CategoryRepository
}
