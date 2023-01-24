package kinopoisk.cinema.di.module

import dagger.Binds
import dagger.Module
import kinopoisk.cinema.data.network.InterceptorKeyImpl
import kinopoisk.cinema.domain.InterceptorKey

@Module
abstract class InterceptorModule {

    @Binds
    abstract fun bindInterceptor(interceptorKeyImpl: InterceptorKeyImpl): InterceptorKey
}
