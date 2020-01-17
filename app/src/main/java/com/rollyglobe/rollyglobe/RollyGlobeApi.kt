package com.rollyglobe.rollyglobe

import com.rollyglobe.rollyglobe.response_model.NationCodeModel
import com.rollyglobe.rollyglobe.response_model.SignUpModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST

class RollyGlobeApi {
    interface API{
        @GET("/codeNumbers/nationCode.json")
        fun getNationCodeInfoList():Observable<List<NationCodeModel>>

        @POST("/ajax/user.php")
        fun SignUp():Observable<SignUpModel>
    }

    companion object {
        fun getNationCodeInfoList():Observable<List<NationCodeModel>>{
            return RetrofitCreator.create(API::class.java)
                .getNationCodeInfoList()
        }
    }
}