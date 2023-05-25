package kinopoisk.cinema.domain.repository

import kinopoisk.cinema.presentation.screen.gallery.TypeSizePhotoUiModel

interface GalleryRepository {

    suspend fun getSizeGallery(id: Int, type: String): Result<List<TypeSizePhotoUiModel>>
}
