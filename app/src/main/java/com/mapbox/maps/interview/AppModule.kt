package com.mapbox.maps.interview

import com.mapbox.maps.interview.search.SearchPhotosApi
import com.mapbox.maps.interview.search.SearchPhotosUseCase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    viewModel { PhotoMapViewModel(get()) }
    single<SearchPhotosApi> { get<Retrofit>().create(SearchPhotosApi::class.java) }
    single { SearchPhotosUseCase(get()) }
}