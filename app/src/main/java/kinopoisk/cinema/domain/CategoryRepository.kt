package kinopoisk.cinema.domain

import kinopoisk.cinema.presentation.screen.homepage.CategoryUiModel

interface CategoryRepository {
    suspend fun getCategories(): List<CategoryUiModel>
}
