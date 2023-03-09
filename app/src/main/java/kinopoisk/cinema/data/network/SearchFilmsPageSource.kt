package kinopoisk.cinema.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kinopoisk.cinema.data.mapper.mapToSearchFilmsModel
import kinopoisk.cinema.presentation.screen.searchpage.SearchModel

class SearchFilmsPageSource @AssistedInject constructor(
    private val kinopoiskApi: KinopoiskApi,
    @Assisted private val query: String
) : PagingSource<Int, SearchModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchModel> {
        if (query.isBlank()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

        val page = params.key ?: ApiConstants.FIRST_PAGE
        return runCatching {
            kinopoiskApi.getSearchFilm(nameFilm = query, page = page).mapToSearchFilmsModel()
        }.fold(
            onSuccess = { searchResponse ->
                LoadResult.Page(
                    data = searchResponse,
                    prevKey = if (page == ApiConstants.FIRST_PAGE) null else page - ApiConstants.FIRST_PAGE,
                    nextKey = if (searchResponse.isEmpty()) null else page + ApiConstants.FIRST_PAGE
                )
            },
            onFailure = { error ->
                LoadResult.Error(error)
            }
        )
    }

    override fun getRefreshKey(state: PagingState<Int, SearchModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(ApiConstants.FIRST_PAGE) ?: anchorPage.nextKey?.minus(ApiConstants.FIRST_PAGE)
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted query: String): SearchFilmsPageSource
    }
}
