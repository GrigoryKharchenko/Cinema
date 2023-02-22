package kinopoisk.cinema.data.mapper

import kinopoisk.cinema.data.network.response.GalleryResponse
import kinopoisk.cinema.presentation.screen.gallery.TypeSizePhotoUiModel

fun GalleryResponse.mapToSizePhotos(): List<TypeSizePhotoUiModel> {
    val list = mutableListOf<TypeSizePhotoUiModel>()

    for (i in items.indices step 3) {
        val firstSmallPhoto = items[i].image
        val secondSmallPhoto = items.getOrNull(i + 1)?.image
        val bigPhoto = items.getOrNull(i + 2)?.image

        list.add(
            TypeSizePhotoUiModel.SmallPhoto(
                firstPhoto = firstSmallPhoto,
                secondPhoto = secondSmallPhoto
            )
        )
        bigPhoto?.let {
            list.add(
                TypeSizePhotoUiModel.BigPhoto(
                    bigPhoto = it
                )
            )
        }
    }
    return list
}
