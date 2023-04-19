package kinopoisk.cinema.presentation.screen.gallery

sealed interface TypeSizePhotoUiModel {

    data class BigPhoto(
        val bigPhoto: String
    ) : TypeSizePhotoUiModel

    data class SmallPhoto(
        val firstPhoto: String,
        val secondPhoto: String?,
    ) : TypeSizePhotoUiModel
}
