package kinopoisk.cinema.di.module

import dagger.Module
import dagger.Provides
import kinopoisk.cinema.BuildConfig
import kinopoisk.cinema.data.network.KinopoiskApi
import kinopoisk.cinema.domain.InterceptorKey
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val TIMEOUT = 100L

@Module
object RetrofitModule {

    @Singleton
    @Provides
    fun filmClient(client: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideFilmsApi(retrofit: Retrofit): KinopoiskApi =
        retrofit.create(KinopoiskApi::class.java)

    @Provides
    @Singleton
    fun provideHttpClient(
        interceptorKey: InterceptorKey,
    ): OkHttpClient {
        val build = OkHttpClient.Builder()
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                interceptorKey.addInterceptorKey(chain)
            }
        if (BuildConfig.DEBUG) {
            build.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        return build.build()
    }
}
