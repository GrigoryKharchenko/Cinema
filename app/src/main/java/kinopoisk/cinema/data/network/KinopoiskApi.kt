package kinopoisk.cinema.data.network

import kinopoisk.cinema.BuildConfig
import kinopoisk.cinema.data.network.response.FilmsResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KinopoiskApi {

    // TODO: а почему не интерцептор с хедером? чтоб не копировать каждый раз
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
}
