package kinopoisk.cinema.data.network.response

import com.google.gson.annotations.SerializedName

class SerialsResponse(
    @SerializedName("total") val countSeason: Int,
    @SerializedName("items") val items: List<SeasonsResponse>
)

class SeasonsResponse(
    @SerializedName("number") val numberSeason: Int,
    @SerializedName("episodes") val episodes: List<EpisodesResponse>
)

class EpisodesResponse(
    @SerializedName("seasonNumber") val seasonNumber: Int,
    @SerializedName("episodeNumber") val episodeNumber: Int,
    @SerializedName("nameRu") val nameRu: String?,
    @SerializedName("nameEn") val nameEn: String?,
    @SerializedName("releaseDate") val releaseDate: String,
)
