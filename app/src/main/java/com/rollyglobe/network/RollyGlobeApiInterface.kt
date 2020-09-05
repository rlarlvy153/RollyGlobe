package com.rollyglobe.network

import com.rollyglobe.network.model.RecommendListResponseModel
import com.rollyglobe.network.model.request_model.*
import com.rollyglobe.network.model.response_model.*
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RollyGlobeApiInterface {

    @GET("/codeNumbers/nationCode.json")
    fun getNationCodeInfoList(): Single<List<NationCodeResponseModel>>

    @POST("/ajax/user.php")
    fun signUp(@Body param: SignUpRequestModel): Single<SignUpResponseModel>

    @POST("/ajax/user.php")
    fun signIn(@Body param: SignInRequestModel): Single<SignInModel>

    @POST("/ajax/spot.php")
    fun getRecommendList(@Body param: RecommendRequestModel): Single<RecommendListResponseModel>

    @POST("/ajax/spot.php")
    fun getSpotInnerContents(@Body param: InnerContentsRequestModel): Single<ResponseBody>

    @POST("/ajax/spot.php")
    fun getGeocodeByGps(@Body param: GeocodeByGpsRequestModel): Observable<GeocodeByGpuResponseModel>

    @POST("ajax/mypage.php")
    fun getMyPageHomeInfo(@Body param: MyPageHomeRequestModel): Single<MyPageHomeInfoResponseModel>

    @POST("ajax/user.php")
    fun editUserName(@Body param: EditUserNameRequestModel): Single<EditUserNameResponseModel>

    @POST("ajax/user.php")
    fun editUserPhoneNumber(@Body param: EditUserPhoneNumberRequestModel): Single<EditUserPhoneNumberResponseModel>

    @POST("ajax/user.php")
    fun editUserEmail(@Body param: EditUserEmailRequestModel): Single<EditUserEmailResponseModel>

    @POST("ajax/user.php")
    fun editUserGender(@Body param: EditUserGenderRequestModel): Single<EditUserGenderResponseModel>

    @POST("ajax/user.php")
    fun editUserBirthday(@Body param: EditUserBirthdayRequestModel): Single<EditUserBirthdayResponseModel>

    @POST("ajax/user.php")
    fun editUserPassword(@Body param: EditUserPasswordRequestModel): Single<EditUserPasswordResponseModel>

    @POST("ajax/comment.php")
    fun loadCommentList(@Body param: LoadCommentListRequestModel): Single<List<LoadCommentListResponseModel>>

}
