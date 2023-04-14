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
    val isVisibleActors: Boolean = true,
    val isVisibleStaff: Boolean = true,
    val isVisibleGallery: Boolean = true,
    val isVisibleSimilar: Boolean = true,
    val sizeActor: String? = null,
    val sizeStuff: String? = null,
    val sizeGallery: String? = null,
    val sizeSimilar: String? = null,
    val isSerial: Boolean = false,
    val countSeason:Int = 0,
    val countEpisodes:Int = 0
)
