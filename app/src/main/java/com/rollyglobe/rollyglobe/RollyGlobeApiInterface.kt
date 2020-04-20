package com.rollyglobe.rollyglobe

import com.rollyglobe.rollyglobe.Model.request_model.*
import com.rollyglobe.rollyglobe.Model.response_model.*

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

        @POST("ajax/user.php")
        fun EditUserName(@Body param: EditUserNameRequestModel) : Single<EditUserNameResponseModel>

}
