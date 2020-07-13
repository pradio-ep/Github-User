package com.pradioep.githubuser.util

import android.content.Context
import com.pradioep.githubuser.R
import okhttp3.*
import java.io.IOException

class ApiInterceptor(private val context: Context): Interceptor{

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val plainQueryParameters = request.url.queryParameterNames
        var httpUrl = request.url

        if (plainQueryParameters.isNotEmpty()) {
            val httpUrlBuilder = httpUrl.newBuilder()
            for (i in plainQueryParameters.indices) {
                val name = httpUrl.queryParameterName(i)
                val value = httpUrl.queryParameterValue(i)
                httpUrlBuilder.setQueryParameter(name, value)
            }
            httpUrl = httpUrlBuilder.build()
        }

        val newRequest: Request
        val requestBuilder = request.newBuilder()

        newRequest = requestBuilder.url(httpUrl)
            .header("Authorization", "token " + context.getString(R.string.github_api_key))
            .build()

        val proceed = chain.proceed(newRequest)

        return proceed.newBuilder().build()
    }
}