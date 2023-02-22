package kinopoisk.cinema.data.mapper

import kinopoisk.cinema.data.network.response.GalleryResponse
import kinopoisk.cinema.presentation.screen.gallery.TypeSizePhotoUiModel

fun GalleryResponse.mapToSizePhoto(): List<TypeSizePhotoUiModel> {
    if (items.isEmpty()) return emptyList()

    val list = mutableListOf<TypeSizePhotoUiModel>()

    for (i in items)

    return list
}
