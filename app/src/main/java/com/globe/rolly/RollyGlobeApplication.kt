package com.globe.rolly

import android.app.Application
import com.globe.BuildConfig
import org.koin.android.ext.koin.androidContext
import timber.log.Timber

class RollyGlobeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppComponents.init(this)

        initTimber()

        startKoin()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun startKoin() {
        org.koin.core.context.startKoin {
            androidContext(this@RollyGlobeApplication)
            modules(koinModulesList)
        }
    }
}