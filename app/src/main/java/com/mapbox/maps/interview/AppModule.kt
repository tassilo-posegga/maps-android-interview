package com.mapbox.maps.interview

import com.mapbox.maps.interview.networking.FlickrApi
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    viewModel { PhotoMapViewModel(get(), get()) }
    single<FlickrApi> { get<Retrofit>().create(FlickrApi::class.java) }
}