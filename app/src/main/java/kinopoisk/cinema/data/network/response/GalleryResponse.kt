package kinopoisk.cinema.data.network.response

import com.google.gson.annotations.SerializedName

data class GalleryResponse(
    @SerializedName("total") val total: String,
    @SerializedName("items") val items: List<ImageResponse>
)

data class ImageResponse(
    @SerializedName("imageUrl") val image: String
)
