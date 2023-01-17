package kinopoisk.cinema.di.module

import dagger.Module
import dagger.Provides
import kinopoisk.cinema.BuildConfig
import kinopoisk.cinema.data.network.KinopoiskApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object RetrofitModule {

    @Singleton
    @Provides
    fun filmClient(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideFilmsApi(retrofit: Retrofit): KinopoiskApi =
        retrofit.create(KinopoiskApi::class.java)
}
