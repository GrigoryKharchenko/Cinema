package kinopoisk.cinema.data.network.response

import com.google.gson.annotations.SerializedName

class SearchFilterResponse(
    @SerializedName("genres") val genres: List<GenresFilterResponse>,
    @SerializedName("countries") val countries: List<CountriesFilterResponse>,
)

class GenresFilterResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("genre") val genre: String
)

class CountriesFilterResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("country") val country: String
)
