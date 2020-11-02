package com.globe.rolly.network

import com.globe.rolly.network.model.RecommendListResponseModel
import com.globe.rolly.network.model.request_model.*
import com.globe.rolly.network.model.spot.geocode.GeocodeByGpsRequestModel
import com.globe.rolly.network.model.spot.geocode.GeocodeByGpsResponseModel
import com.globe.rolly.network.model.spot.geocode.GeocodeByPlaceIdRequestModel
import com.globe.rolly.network.model.spot.geocode.GeocodeByPlaceIdResponseModel
import com.globe.rolly.network.model.edit_user_info.birthday.EditUserBirthdayRequestModel
import com.globe.rolly.network.model.edit_user_info.birthday.EditUserBirthdayResponseModel
import com.globe.rolly.network.model.edit_user_info.email.EditUserEmailRequestModel
import com.globe.rolly.network.model.edit_user_info.email.EditUserEmailResponseModel
import com.globe.rolly.network.model.edit_user_info.gender.EditUserGenderRequestModel
import com.globe.rolly.network.model.edit_user_info.gender.EditUserGenderResponseModel
import com.globe.rolly.network.model.edit_user_info.name.EditUserNameRequestModel
import com.globe.rolly.network.model.edit_user_info.name.EditUserNameResponseModel
import com.globe.rolly.network.model.edit_user_info.password.EditUserPasswordRequestModel
import com.globe.rolly.network.model.edit_user_info.password.EditUserPasswordResponseModel
import com.globe.rolly.network.model.edit_user_info.phonenumber.EditUserPhoneNumberRequestModel
import com.globe.rolly.network.model.edit_user_info.phonenumber.EditUserPhoneNumberResponseModel
import com.globe.rolly.network.model.response_model.*
import com.globe.rolly.network.model.user.signin.SignInModel
import com.globe.rolly.network.model.user.signin.SignInRequestModel
import com.globe.rolly.network.model.user.signup.SignUpRequestModel
import com.globe.rolly.network.model.user.signup.SignUpResponseModel
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
    fun getGeocodeByGps(@Body param: GeocodeByGpsRequestModel): Observable<GeocodeByGpsResponseModel>

    @POST("/ajax/spot.php")
    fun getGeocodeByPlaceId(@Body param: GeocodeByPlaceIdRequestModel): Observable<GeocodeByPlaceIdResponseModel>

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
