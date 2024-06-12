package com.shu.network.di

import com.shu.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

private const val AUTHORIZATION_HEADER = "x-api-key"
private const val HASH = "hash"
open class AuthorizationInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return proceedRequestWithAuthorization(chain)
    }

    private fun proceedRequestWithAuthorization(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder().build()
        request = request.newBuilder().url(url).header(AUTHORIZATION_HEADER,BuildConfig.API_KEY).build()
        return chain.proceed(request)
    }
}