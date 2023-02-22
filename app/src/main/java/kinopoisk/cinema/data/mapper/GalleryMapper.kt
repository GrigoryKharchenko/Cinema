package kinopoisk.cinema.data.mapper

import kinopoisk.cinema.data.network.response.GalleryResponse
import kinopoisk.cinema.presentation.screen.gallery.TypeSizePhotoUiModel

fun GalleryResponse.mapToSizePhotos(): List<TypeSizePhotoUiModel> {
    if (items.isEmpty()) return emptyList()
    // список в котором будут лежать фото
    val list = mutableListOf<TypeSizePhotoUiModel>()
    // элемент для в который будут помещаться фото
    var localItem: String? = null
    // промежуток в диапозоне от 1 до размера элементов исключая последний элемент
    for (i in 1 until items.size + 1) {
        // текущее фото которое берется из респонса по индексу - 1,
        // потому что отчет начинается с 0, а цикле отчет начинает с 1
        val image = items[i - 1].image
        if (localItem == null) {
            localItem = image
            // продолжает выполнение цикла со следующего его шага, без обработки оставшегося кода текущей итерации
            continue
        }
        // если индекс кратен 3 то мы добавляем большое фото и зануляем элемент
        if (i % 3 == 0) {
            list.add(
                TypeSizePhotoUiModel.BigPhoto(
                    bigPhoto = image
                )
            )
            localItem = null
            //иначе мы добавляем 2 маленьких фото
        } else {
            list.add(
                TypeSizePhotoUiModel.SmallPhoto(
                    firstPhoto = localItem,
                    secondPhoto = image
                )
            )
        }
    }
    return list
}
