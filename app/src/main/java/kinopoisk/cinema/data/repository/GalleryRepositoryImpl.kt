package kinopoisk.cinema.data.repository

import kinopoisk.cinema.data.mapper.mapToSizePhotos
import kinopoisk.cinema.data.network.KinopoiskApi
import kinopoisk.cinema.domain.repository.GalleryRepository
import kinopoisk.cinema.presentation.screen.gallery.TypeSizePhotoUiModel
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val kinopoiskApi: KinopoiskApi
) : GalleryRepository {

    override suspend fun getSizeGallery(id: Int, type: String): Result<List<TypeSizePhotoUiModel>> =
        runCatching {
            kinopoiskApi.getGallery(id, type = type).mapToSizePhotos()
        }
}
