package com.globe.rolly.support.di

import com.globe.rolly.network.AppRetrofitBuilder
import com.globe.rolly.network.NetworkConfig
import com.globe.rolly.network.RollyGlobeApiClient
import com.globe.rolly.network.RollyGlobeApiInterface
import org.koin.dsl.module

val networkModule = module {
    single {
        RollyGlobeApiClient(
            AppRetrofitBuilder(NetworkConfig.API_BASE_URL)
                .build()
                .create(RollyGlobeApiInterface::class.java)
        )
    }
}