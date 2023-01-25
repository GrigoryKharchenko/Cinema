package kinopoisk.cinema.presentation.screen.filmdetail.model

data class FilmDetailUiModel(
    val detailFilm: FilmDetailModel? = null,
    val actor: List<StaffModel>? = null,
    val gallery: List<GalleryModel>? = null,
    val similar: List<SimilarFilmModel>? = null,
    val staff: List<StaffModel>? = null,
    val isVisibleTextError: Boolean = false,
    val isVisibleProgress: Boolean = false,
    val isVisibleAppBar: Boolean = true,
    val isVisibleNestedScroll: Boolean = true,
    val isActorsVisible: Boolean = true,
    val isStaffVisible: Boolean = true,
    val isGalleryVisible: Boolean = true,
    val isSimilarVisible: Boolean = true,
)
