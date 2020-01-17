package com.rollyglobe.rollyglobe

import com.rollyglobe.rollyglobe.response_model.NationCodeModel
import com.rollyglobe.rollyglobe.response_model.SignUpModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface RollyGlobeApiInterface {

        @GET("/codeNumbers/nationCode.json")
        fun getNationCodeInfoList(): Call<List<NationCodeModel>>

        @POST("/ajax/user.php")
        fun SignUp():Observable<SignUpModel>
}
