package kinopoisk.cinema.data.network

import kinopoisk.cinema.BuildConfig
import kinopoisk.cinema.data.network.response.ActorResponse
import kinopoisk.cinema.data.network.response.DetailFilmResponse
import kinopoisk.cinema.data.network.response.FilmsResponse
import kinopoisk.cinema.data.network.response.GalleryResponse
import kinopoisk.cinema.data.network.response.SimilarsResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskApi {

    @GET("/api/v2.2/films/premieres?year=2023&month=FEBRUARY")
    @Headers("X-API-KEY:${BuildConfig.API_KEY}")
    suspend fun getPremieres(): FilmsResponse

    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS&page=1")
    @Headers("X-API-KEY:${BuildConfig.API_KEY}")
    suspend fun getPopularFilms(): FilmsResponse

    @GET("/api/v2.2/films")
    @Headers("X-API-KEY:${BuildConfig.API_KEY}")
    suspend fun getFirstRandomFilms(
        @Query("countries") countries: String,
        @Query("genres") genres: String,
    ): FilmsResponse

    @GET("/api/v2.2/films")
    @Headers("X-API-KEY:${BuildConfig.API_KEY}")
    suspend fun getSecondRandomFilms(
        @Query("countries") countries: String,
        @Query("genres") genres: String,
    ): FilmsResponse

    @GET("/api/v2.2/films/top?type=TOP_250_BEST_FILMS&page=1")
    @Headers("X-API-KEY:${BuildConfig.API_KEY}")
    suspend fun getTopFilms(): FilmsResponse

    @GET("/api/v2.2/films?type=TV_SERIES")
    @Headers("X-API-KEY:${BuildConfig.API_KEY}")
    suspend fun getSerial(): FilmsResponse

    @GET("/api/v2.2/films/{id}")
    @Headers("X-API-KEY:${BuildConfig.API_KEY}")
    suspend fun getDetailFilm(
        @Path("id") id: Int
    ): DetailFilmResponse

    @GET("/api/v1/staff")
    @Headers("X-API-KEY:${BuildConfig.API_KEY}")
    suspend fun getFilmCrew(
        @Query("filmId") id: Int
    ): List<ActorResponse>

    @GET("/api/v2.2/films/{id}/images?page=1")
    @Headers("X-API-KEY:${BuildConfig.API_KEY}")
    suspend fun getGallery(
        @Path("id") id: Int
    ): GalleryResponse

    @GET("/api/v2.2/films/{id}/similars")
    @Headers("X-API-KEY:${BuildConfig.API_KEY}")
    suspend fun getSimilars(
        @Path("id") id: Int
    ): SimilarsResponse
}
