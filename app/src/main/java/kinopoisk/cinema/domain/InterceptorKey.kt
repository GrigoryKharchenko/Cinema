package kinopoisk.cinema.domain

import okhttp3.Interceptor
import okhttp3.Response

interface InterceptorKey {

    fun addInterceptorKey(chain: Interceptor.Chain): Response
}
