package call.ofbeer.api

import okhttp3.Interceptor
import okhttp3.Response

class CookieManager(): Interceptor {

    companion object {
        const val COOKIE_KEY = "Cookie"
        const val SET_COOKIE_KEY = "Set-Cookie"
    }

    fun clearCookie() {
        cookie = null
    }

    private var cookie: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
        cookie?.let { requestBuilder.addHeader(COOKIE_KEY, it) }

        val response = chain.proceed(requestBuilder.build())
        response.headers
            .toMultimap()[SET_COOKIE_KEY]
            ?.filter { !it.contains("laravel_session") }
            ?.getOrNull(0)
            ?.also {
                cookie = it
            }

        return response
    }
}