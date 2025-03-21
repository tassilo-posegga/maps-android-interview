package com.mapbox.maps.domain.search.api

import com.mapbox.maps.domain.search.internal.data.SearchPhotosApi
import com.mapbox.maps.domain.search.internal.domain.SearchPhotosUseCase
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

public val searchModule: Module = module {
    single<SearchPhotosApi> { get<Retrofit>().create(SearchPhotosApi::class.java) }
    single { SearchPhotosUseCase(get()) }

    // public api
    single<SearchPhotos> { SearchPhotosUseCase(get()) }
}