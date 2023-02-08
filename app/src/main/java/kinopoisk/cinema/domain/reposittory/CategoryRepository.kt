package kinopoisk.cinema.domain.reposittory

import kinopoisk.cinema.presentation.screen.homepage.CategoryUiModel

interface CategoryRepository {
    suspend fun getCategories(): List<CategoryUiModel>
}
