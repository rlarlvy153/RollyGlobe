package com.rollyglobe

object RestClient {
    var restClient = RetrofitCreator.getRetrofitService(RollyGlobeApiInterface::class.java)
}