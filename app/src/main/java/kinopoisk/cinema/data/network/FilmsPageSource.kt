package kinopoisk.cinema.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kinopoisk.cinema.data.TypeCategories
import kinopoisk.cinema.data.mapper.mapToAllAnotherFilmsModel
import kinopoisk.cinema.data.mapper.mapToAllFilmsModel
import kinopoisk.cinema.presentation.screen.films.FilmModel

class FilmsPageSource @AssistedInject constructor(
    private val kinopoiskApi: KinopoiskApi,
    @Assisted private val typeCategory: TypeCategories
) : PagingSource<Int, FilmModel>() {

    override fun getRefreshKey(state: PagingState<Int, FilmModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(ApiConstants.FIRST_PAGE) ?: anchorPage.nextKey?.minus(ApiConstants.FIRST_PAGE)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmModel> {
        val page = params.key ?: ApiConstants.FIRST_PAGE
        return runCatching {
            getFilms(page)
        }.fold(
            onSuccess = { filmResponse ->
                LoadResult.Page(
                    data = filmResponse,
                    prevKey = if (page == ApiConstants.FIRST_PAGE) null else page - ApiConstants.FIRST_PAGE,
                    nextKey = if (filmResponse.isEmpty()) null else page + ApiConstants.FIRST_PAGE
                )
            },
            onFailure = { error ->
                LoadResult.Error(error)
            }
        )
    }

    private suspend fun getFilms(page: Int): List<FilmModel> {
        return when (val typeCategory = typeCategory) {
            is TypeCategories.Top -> kinopoiskApi.getTopFilms(
                page = page,
                type = typeCategory.type
            ).mapToAllFilmsModel()
            is TypeCategories.Random -> kinopoiskApi.getRandomCategory(
                countries = typeCategory.countryCode,
                genres = typeCategory.genresCode,
                page = page
            ).mapToAllAnotherFilmsModel()
            is TypeCategories.Serials -> kinopoiskApi.getSerial(
                page = page,
                type = typeCategory.type
            ).mapToAllAnotherFilmsModel()
            is TypeCategories.Premieres -> kinopoiskApi.getPremieres().mapToAllAnotherFilmsModel()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted typeCategories: TypeCategories): FilmsPageSource
    }
}
