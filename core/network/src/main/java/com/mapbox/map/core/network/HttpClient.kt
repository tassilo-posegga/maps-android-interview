package com.mapbox.map.core.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

private const val CONNECTION_TIMEOUT_MS: Long = 10

internal object HttpClient {
    val okHttpClient = OkHttpClient.Builder().connectTimeout(
        CONNECTION_TIMEOUT_MS,
        TimeUnit.SECONDS
    ).addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }).build()
}
