package kinopoisk.cinema.presentation.screen.filmdetail.model

data class ErrorUiModel(
    val isVisibleTextError: Boolean = false,
    val isVisibleProgress: Boolean = false,
    val isVisibleAppBar: Boolean = true,
    val isVisibleNestedScroll: Boolean = true,
    val isVisibleTitleActor: Boolean = false,
    val isVisibleCountActor: Boolean = false,
    val isVisibleTitleStaff: Boolean = false,
    val isVisibleCountStaff: Boolean = false,
    val isVisibleTitleGallery: Boolean = false,
    val isVisibleCountGallery: Boolean = false,
    val isVisibleTitleSimilar: Boolean = false,
    val isVisibleCountSimilar: Boolean = false,
)
