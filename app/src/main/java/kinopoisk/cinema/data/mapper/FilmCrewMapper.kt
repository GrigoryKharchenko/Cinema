package kinopoisk.cinema.data.mapper

import kinopoisk.cinema.data.network.response.ActorResponse
import kinopoisk.cinema.data.network.response.GalleryResponse
import kinopoisk.cinema.data.network.response.ImageResponse
import kinopoisk.cinema.data.network.response.SimilarResponse
import kinopoisk.cinema.data.network.response.SimilarsResponse
import kinopoisk.cinema.presentation.screen.filmdetail.adpters.filmcrew.FilmCrewUiModel
import kinopoisk.cinema.presentation.screen.filmdetail.adpters.gallery.GalleryUiModel
import kinopoisk.cinema.presentation.screen.filmdetail.adpters.similar.SimilarUiModel

fun List<ActorResponse>.mapToFilmCrewsModel(): List<FilmCrewUiModel> =
    this.map(ActorResponse::mapToFilmCrewModel)

fun ActorResponse.mapToFilmCrewModel(): FilmCrewUiModel =
    FilmCrewUiModel(
        id = id,
        name = nameRu ?: nameEn,
        photo = photo,
        character = character,
        profession = profession
    )

fun GalleryResponse.mapToGalleryModel(): List<GalleryUiModel> =
    this.items.map(ImageResponse::mapToImageModel)

fun ImageResponse.mapToImageModel(): GalleryUiModel =
    GalleryUiModel(
        image = image,
    )

fun SimilarsResponse.mapToSimilarsModel(): List<SimilarUiModel> =
    this.items.map(SimilarResponse::mapToSimilarModel)

fun SimilarResponse.mapToSimilarModel(): SimilarUiModel =
    SimilarUiModel(
        id = id,
        name = nameRu ?: nameEn,
        poster = poster,
    )
