package kinopoisk.cinema.domain.repository

import kinopoisk.cinema.presentation.screen.homepage.CategoryUiModel

interface CategoryRepository {
    suspend fun getCategories(): List<CategoryUiModel>
}
