package kinopoisk.cinema.di.module

import dagger.Binds
import dagger.Module
import kinopoisk.cinema.data.network.ApiKeyInterceptor
import okhttp3.Interceptor

@Module
interface InterceptorModule {

    @Binds
    fun bindInterceptor(apiKeyInterceptor: ApiKeyInterceptor): Interceptor
}
