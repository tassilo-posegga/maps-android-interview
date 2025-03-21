package com.mapbox.map.core.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.flickr.com/services/rest/"

val networkModule = module {
    single { HttpClient.okHttpClient }
    single {
        // TODO this retrofit instance is Flick specific and should move into a flickr module
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create()
                )
            )
            .client(get())
            .build()
    }
}