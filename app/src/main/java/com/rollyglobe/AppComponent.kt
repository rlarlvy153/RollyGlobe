package com.rollyglobe

import android.content.Context

object AppComponent {
    lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context
    }
}