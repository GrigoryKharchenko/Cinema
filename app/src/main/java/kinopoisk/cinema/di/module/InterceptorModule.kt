package kinopoisk.cinema.di.module

import dagger.Binds
import dagger.Module
import kinopoisk.cinema.data.network.ApiKeyInterceptor
import okhttp3.Interceptor

// TODO interface
@Module
abstract class InterceptorModule {

    @Binds
    abstract fun bindInterceptor(apiKeyInterceptor: ApiKeyInterceptor): Interceptor
}
