package kinopoisk.cinema.presentation.screen.filmdetail.model

data class ErrorUiModel(
    val isVisibleTextError: Boolean = false,
    val isVisibleProgress: Boolean = false,
    val isVisibleAppBar: Boolean = false,
    val isVisibleNestedScroll: Boolean = false,
    val isVisibleTitleActor: Boolean = false,
    val isVisibleCountActor: Boolean = false,
    val isVisibleTitleStuff: Boolean = false,
    val isVisibleCountStuff: Boolean = false,
    val isVisibleTitleGallery: Boolean = false,
    val isVisibleCountGallery: Boolean = false,
    val isVisibleTitleSimilar: Boolean = false,
    val isVisibleCountSimilar: Boolean = false,
)
