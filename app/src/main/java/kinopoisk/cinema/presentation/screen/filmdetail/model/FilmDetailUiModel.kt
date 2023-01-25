package kinopoisk.cinema.presentation.screen.filmdetail.model

data class FilmDetailUiModel(
    val detailFilm: FilmDetailModel,
    val actor: List<StaffModel> = emptyList(),
    val gallery: List<GalleryModel> = emptyList(),
    val similar: List<SimilarFilmModel> = emptyList(),
    val staff: List<StaffModel> = emptyList(),
    val isVisibleTextError: Boolean = false,
    val isVisibleProgress: Boolean = false,
    val isVisibleAppBar: Boolean = true,
    val isVisibleNestedScroll: Boolean = true,
    val isVisibleTitleActor: Boolean = true,
    val isVisibleCountActor: Boolean = true,
    val isVisibleTitleStaff: Boolean = true,
    val isVisibleCountStaff: Boolean = true,
    val isVisibleTitleGallery: Boolean = true,
    val isVisibleCountGallery: Boolean = true,
    val isVisibleTitleSimilar: Boolean = true,
    val isVisibleCountSimilar: Boolean = true,
)
