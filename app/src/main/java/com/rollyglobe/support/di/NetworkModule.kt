package com.rollyglobe.support.di

import com.rollyglobe.network.AppRetrofitBuilder
import com.rollyglobe.network.NetworkConfig
import com.rollyglobe.network.RollyGlobeApiClient
import com.rollyglobe.network.RollyGlobeApiInterface
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