package com.rollyglobe.network

object RestClient {
    var restClient = RetrofitCreator.getRetrofitService(RollyGlobeApiInterface::class.java)
}