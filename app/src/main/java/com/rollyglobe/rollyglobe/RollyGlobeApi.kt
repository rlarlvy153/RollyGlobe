package com.rollyglobe.rollyglobe

import com.rollyglobe.rollyglobe.response_model.NationCodeModel
import io.reactivex.Observable
import retrofit2.http.GET

class RollyGlobeApi {
    interface getNationCodeInfoImpl{
        @GET("/codeNumbers/nationCode.json")
        fun getNationCodeInfoList():Observable<List<NationCodeModel>>
    }

    companion object {
        fun getNationCodeInfoList():Observable<List<NationCodeModel>>{
            return RetrofitCreator.create(getNationCodeInfoImpl::class.java)
                .getNationCodeInfoList()
        }
    }
}