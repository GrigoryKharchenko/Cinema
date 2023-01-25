package kinopoisk.cinema.data.network

import kinopoisk.cinema.data.network.response.DetailFilmResponse
import kinopoisk.cinema.data.network.response.FilmsResponse
import kinopoisk.cinema.data.network.response.GalleryResponse
import kinopoisk.cinema.data.network.response.SimilarsResponse
import kinopoisk.cinema.data.network.response.StaffResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskApi {

    @GET("/api/v2.2/films/premieres")
    suspend fun getPremieres(
        @Query("year")
        year: String = "2022",
        @Query("month")
        month: String = "FEBRUARY"
    ): FilmsResponse

    @GET("/api/v2.2/films/top")
    suspend fun getTopFilms(
        @Query("page")
        @androidx.annotation.IntRange(from = 1)
        page: Int = ApiConstants.FIRST_PAGE,
        @Query("type")
        type: String
    ): FilmsResponse

    @GET("/api/v2.2/films")
    suspend fun getRandomCategory(
        @Query("countries") countries: String,
        @Query("genres") genres: String,
        @Query("page")
        @androidx.annotation.IntRange(from = 1)
        page: Int = ApiConstants.FIRST_PAGE
    ): FilmsResponse

    @GET("/api/v2.2/films")
    suspend fun getSerial(
        @Query("page")
        @androidx.annotation.IntRange(from = 1)
        page: Int = ApiConstants.FIRST_PAGE,
        @Query("type")
        type: String = ApiConstants.TV_SERIES
    ): FilmsResponse

    @GET("/api/v2.2/films/{id}")
    suspend fun getDetailFilm(
        @Path("id") id: Int
    ): DetailFilmResponse

    @GET("/api/v1/staff")
    suspend fun getStaff(
        @Query("filmId") id: Int
    ): List<StaffResponse>

    @GET("/api/v2.2/films/{id}/images?page=1")
    suspend fun getGallery(
        @Path("id") id: Int
    ): GalleryResponse

    @GET("/api/v2.2/films/{id}/similars")
    suspend fun getSimilars(
        @Path("id") id: Int
    ): SimilarsResponse
}
