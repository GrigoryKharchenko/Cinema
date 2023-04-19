package kinopoisk.cinema.data.network

import kinopoisk.cinema.data.network.response.ActorResponse
import kinopoisk.cinema.data.network.response.DetailFilmResponse
import kinopoisk.cinema.data.network.response.FilmsResponse
import kinopoisk.cinema.data.network.response.GalleryResponse
import kinopoisk.cinema.data.network.response.SearchFilmsResponse
import kinopoisk.cinema.data.network.response.SerialsResponse
import kinopoisk.cinema.data.network.response.SearchFilterResponse
import kinopoisk.cinema.data.network.response.SimilarsResponse
import kinopoisk.cinema.data.network.response.StaffResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskApi {

    @GET("/api/v2.2/films/premieres")
    suspend fun getPremieres(
        @Query("year")
        year: String = ApiConstants.YEAR,
        @Query("month")
        month: String = ApiConstants.MONTH
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

    @GET("/api/v2.2/films/{id}/images")
    suspend fun getGallery(
        @Path("id") id: Int,
        @Query("page")
        @androidx.annotation.IntRange(from = 1)
        page: Int = ApiConstants.FIRST_PAGE,
        @Query("type") type: String = ""
    ): GalleryResponse

    @GET("/api/v2.2/films/{id}/similars")
    suspend fun getSimilars(
        @Path("id") id: Int
    ): SimilarsResponse

    @GET("/api/v1/staff/{id}")
    suspend fun getActor(
        @Path("id") id: Int
    ): ActorResponse

    @GET("/api/v2.2/films")
    suspend fun getSearchFilm(
        @Query("keyword") nameFilm: String? = null,
        @Query("page")
        @androidx.annotation.IntRange(from = 1)
        page: Int = ApiConstants.FIRST_PAGE,
    ): SearchFilmsResponse

    @GET("/api/v2.2/films/filters")
    suspend fun getFilters(): SearchFilterResponse

    @GET("/api/v2.2/films/{id}/seasons")
    suspend fun getSerial(
        @Path("id") id: Int
    ): SerialsResponse
}
