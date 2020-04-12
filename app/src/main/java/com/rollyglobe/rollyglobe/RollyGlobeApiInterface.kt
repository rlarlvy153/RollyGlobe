package com.rollyglobe.rollyglobe

import com.rollyglobe.rollyglobe.Model.request_model.InnerContentsRequestModel
import com.rollyglobe.rollyglobe.Model.request_model.RecommendRequestModel
import com.rollyglobe.rollyglobe.Model.request_model.SignInRequestModel
import com.rollyglobe.rollyglobe.Model.request_model.SignUpRequestModel
import com.rollyglobe.rollyglobe.Model.response_model.NationCodeResponseModel
import com.rollyglobe.rollyglobe.Model.response_model.SignInModel
import com.rollyglobe.rollyglobe.Model.response_model.SignUpResponseModel
import com.rollyglobe.rollyglobe.Model.request_model.MyPageHomeRequestModel
import com.rollyglobe.rollyglobe.Model.response_model.MyPageHomeInfoResponseModel

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.*

interface RollyGlobeApiInterface {

        @GET("/codeNumbers/nationCode.json")
        fun getNationCodeInfoList(): Single<List<NationCodeResponseModel>>

        @POST("/ajax/user.php")
        fun SignUp(@Body param : SignUpRequestModel): Single<SignUpResponseModel>

        @POST("/ajax/user.php")
        fun SignIn(@Body param: SignInRequestModel):Single<SignInModel>

        @POST("/ajax/spot.php")
        fun getRecommendList(@Body param: RecommendRequestModel): Single<ResponseBody>

        @POST("/ajax/spot.php")
        fun getSpotInnerContents(@Body param: InnerContentsRequestModel): Single<ResponseBody>

        @POST("ajax/mypage.php")
        fun getMyPageHomeInfo(@Body param: MyPageHomeRequestModel): Single<MyPageHomeInfoResponseModel>


}
