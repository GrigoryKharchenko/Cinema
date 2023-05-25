package kinopoisk.cinema.presentation.screen.gallery

sealed interface GalleryUiState {

    object Loading : GalleryUiState
    object Error : GalleryUiState
    data class Success(
        val photos: List<TypeSizePhotoUiModel>
    ) : GalleryUiState
}
