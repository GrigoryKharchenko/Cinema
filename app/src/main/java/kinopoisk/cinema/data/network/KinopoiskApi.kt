package kinopoisk.cinema.data.network

import kinopoisk.cinema.data.network.response.DetailFilmResponse
import kinopoisk.cinema.data.network.response.FilmsResponse
import kinopoisk.cinema.data.network.response.GalleryResponse
import kinopoisk.cinema.data.network.response.SimilarsResponse
import kinopoisk.cinema.data.network.response.StuffResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskApi {

    @GET("/api/v2.2/films/premieres?year=2023&month=FEBRUARY")
    suspend fun getPremieres(): FilmsResponse

    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS&page=1")
    suspend fun getPopularFilms(): FilmsResponse

    @GET("/api/v2.2/films")
    suspend fun getFirstRandomFilms(
        @Query("countries") countries: String,
        @Query("genres") genres: String,
    ): FilmsResponse

    @GET("/api/v2.2/films")
    suspend fun getSecondRandomFilms(
        @Query("countries") countries: String,
        @Query("genres") genres: String,
    ): FilmsResponse

    @GET("/api/v2.2/films/top?type=TOP_250_BEST_FILMS&page=1")
    suspend fun getTopFilms(): FilmsResponse

    @GET("/api/v2.2/films?type=TV_SERIES")
    suspend fun getSerial(): FilmsResponse

    @GET("/api/v2.2/films/{id}")
    suspend fun getDetailFilm(
        @Path("id") id: Int
    ): DetailFilmResponse

    @GET("/api/v1/staff")
    suspend fun getStuff(
        @Query("filmId") id: Int
    ): List<StuffResponse>

    @GET("/api/v2.2/films/{id}/images?page=1")
    suspend fun getGallery(
        @Path("id") id: Int
    ): GalleryResponse

    @GET("/api/v2.2/films/{id}/similars")
    suspend fun getSimilars(
        @Path("id") id: Int
    ): SimilarsResponse
}
