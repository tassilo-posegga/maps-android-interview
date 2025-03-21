package com.mapbox.maps.interview

import android.app.Application
import com.mapbox.map.core.network.networkModule
import com.mapbox.maps.domain.search.api.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(networkModule, appModule, searchModule)
        }
    }
}