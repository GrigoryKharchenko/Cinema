package kinopoisk.cinema.data.network.response

import com.google.gson.annotations.SerializedName
import kinopoisk.cinema.presentation.screen.filmdetail.TypeStaff

data class StaffResponse(
    @SerializedName("staffId") val id: Int,
    @SerializedName("nameRu") val nameRu: String?,
    @SerializedName("nameEn") val nameEn: String,
    @SerializedName("description") val character: String?,
    @SerializedName("posterUrl") val photo: String,
    @SerializedName("professionKey") val profession: TypeStaff,
)
