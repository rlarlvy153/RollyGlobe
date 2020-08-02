package com.rollyglobe

import android.app.Application

class RollyGlobeApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        AppComponent.init(this)
    }
}