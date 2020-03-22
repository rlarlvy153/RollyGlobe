package com.rollyglobe.rollyglobe

object RestClient {
    var restClient = RetrofitCreator.getRetrofitService(RollyGlobeApiInterface::class.java)
}