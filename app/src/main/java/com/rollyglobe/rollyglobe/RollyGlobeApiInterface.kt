package com.rollyglobe.rollyglobe

import com.rollyglobe.rollyglobe.request_model.SignInRequestModel
import com.rollyglobe.rollyglobe.request_model.SignUpRequestModel
import com.rollyglobe.rollyglobe.response_model.NationCodeResponseModel
import com.rollyglobe.rollyglobe.response_model.SignInModel
import com.rollyglobe.rollyglobe.response_model.SignUpResponseModel
import io.reactivex.Single
import retrofit2.http.*

interface RollyGlobeApiInterface {

        @GET("/codeNumbers/nationCode.json")
        fun getNationCodeInfoList(): Single<List<NationCodeResponseModel>>

        @POST("/ajax/user.php")
        fun SignUp(@Body param : SignUpRequestModel): Single<SignUpResponseModel>

        @POST("/ajax/user.php")
        fun SignIn(@Body param: SignInRequestModel):Single<SignInModel>

}
