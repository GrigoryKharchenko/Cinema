package kinopoisk.cinema.data.network.response

import com.google.gson.annotations.SerializedName

class SerialsResponse(
    @SerializedName("total") val countSeason: Int,
    @SerializedName("items") val items: List<SeasonsResponse>
)

class SeasonsResponse(
    @SerializedName("number") val numberSeason: String,
    @SerializedName("episodes") val episodes: List<EpisodesResponse>
)

class EpisodesResponse(
    @SerializedName("seasonNumber") val seasonNumber: String,
    @SerializedName("episodeNumber") val episodeNumber: String,
    @SerializedName("nameRu") val nameRu: String?,
    @SerializedName("nameEn") val nameEn: String?,
    @SerializedName("releaseDate") val releaseDate: String,
)
