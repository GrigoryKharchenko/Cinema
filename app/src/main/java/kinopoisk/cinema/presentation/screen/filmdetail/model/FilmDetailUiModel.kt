package kinopoisk.cinema.presentation.screen.filmdetail.model

data class FilmDetailUiModel(
    val detailFilm: FilmDetailModel,
    val actor: List<StuffModel> = emptyList(),
    val gallery: List<GalleryModel> = emptyList(),
    val similar: List<SimilarFilmModel> = emptyList(),
    val stuff: List<StuffModel> = emptyList(),
    val isVisibleTextError: Boolean = false,
    val isVisibleProgress: Boolean = false,
    val isVisibleAppBar: Boolean = true,
    val isVisibleNestedScroll: Boolean = true,
    val isVisibleTitleActor: Boolean = true,
    val isVisibleCountActor: Boolean = true,
    val isVisibleTitleStuff: Boolean = true,
    val isVisibleCountStuff: Boolean = true,
    val isVisibleTitleGallery: Boolean = true,
    val isVisibleCountGallery: Boolean = true,
    val isVisibleTitleSimilar: Boolean = true,
    val isVisibleCountSimilar: Boolean = true,
)
