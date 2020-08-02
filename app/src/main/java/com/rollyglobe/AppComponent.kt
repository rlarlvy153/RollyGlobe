package com.rollyglobe

import android.content.Context

object AppComponent {
    lateinit var applicationContext: Context

    fun setApplicationContext(context: Context) {
        applicationContext = context
    }
}