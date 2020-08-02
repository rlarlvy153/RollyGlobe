package com.rollyglobe.network

import com.rollyglobe.network.model.RecommendListResponseModel
import com.rollyglobe.network.model.request_model.*
import com.rollyglobe.network.model.response_model.*

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
        fun getRecommendList(@Body param: RecommendRequestModel): Single<RecommendListResponseModel>

        @POST("/ajax/spot.php")
        fun getSpotInnerContents(@Body param: InnerContentsRequestModel): Single<ResponseBody>

        @POST("ajax/mypage.php")
        fun getMyPageHomeInfo(@Body param: MyPageHomeRequestModel): Single<MyPageHomeInfoResponseModel>

        @POST("ajax/user.php")
        fun EditUserName(@Body param: EditUserNameRequestModel) : Single<EditUserNameResponseModel>

        @POST("ajax/user.php")
        fun EditUserPhoneNumber(@Body param: EditUserPhoneNumberRequestModel) : Single<EditUserPhoneNumberResponseModel>

        @POST("ajax/user.php")
        fun EditUserEmail(@Body param: EditUserEmailRequestModel) : Single<EditUserEmailResponseModel>

        @POST("ajax/user.php")
        fun EditUserGender(@Body param: EditUserGenderRequestModel) : Single<EditUserGenderResponseModel>

        @POST("ajax/user.php")
        fun EditUserBirthday(@Body param: EditUserBirthdayRequestModel) : Single<EditUserBirthdayResponseModel>

        @POST("ajax/user.php")
        fun EditUserPassword(@Body param: EditUserPasswordRequestModel) : Single<EditUserPasswordResponseModel>

        @POST("ajax/comment.php")
        fun LoadCommentList(@Body param: LoadCommentListRequestModel) : Single<List<LoadCommentListResponseModel>>

}
