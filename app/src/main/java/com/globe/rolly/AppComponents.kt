package com.globe.rolly

import android.content.Context

object AppComponents {
    lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context
    }
}