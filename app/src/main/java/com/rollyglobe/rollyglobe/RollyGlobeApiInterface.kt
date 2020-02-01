package com.rollyglobe.rollyglobe

import com.rollyglobe.rollyglobe.request_model.SignUpRequestModel
import com.rollyglobe.rollyglobe.response_model.NationCodeModel
import com.rollyglobe.rollyglobe.response_model.SignUpModel
import retrofit2.Call
import retrofit2.http.*

interface RollyGlobeApiInterface {

        @GET("/codeNumbers/nationCode.json")
        fun getNationCodeInfoList(): Call<List<NationCodeModel>>

        @POST("/ajax/user.php")
        fun SignUp(@Body param : SignUpRequestModel):Call<SignUpModel>


}
