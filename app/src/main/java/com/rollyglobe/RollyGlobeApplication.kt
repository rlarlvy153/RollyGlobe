package com.rollyglobe

import android.app.Application
import org.koin.android.ext.koin.androidContext
import timber.log.Timber

class RollyGlobeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppComponent.init(this)

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