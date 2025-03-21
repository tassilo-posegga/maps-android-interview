package com.mapbox.maps.interview

import com.mapbox.maps.interview.photomap.PhotoMapViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { PhotoMapViewModel(get()) }
}