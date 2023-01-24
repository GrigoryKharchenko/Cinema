package kinopoisk.cinema.data.network

import kinopoisk.cinema.BuildConfig
import kinopoisk.cinema.domain.InterceptorKey
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class InterceptorKeyImpl @Inject constructor() : InterceptorKey {

    override fun addInterceptorKey(chain: Interceptor.Chain): Response {
        val request = chain
            .request()
            .newBuilder()
            .addHeader("X-API-KEY", BuildConfig.API_KEY)
            .build()
        return chain.proceed(request)
    }
}
