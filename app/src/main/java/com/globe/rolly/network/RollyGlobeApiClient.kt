package com.globe.rolly.network

import com.globe.rolly.network.model.RecommendListResponseModel
import com.globe.rolly.network.model.SpotInnerContentsResponseModel
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
import com.globe.rolly.network.model.request_model.*
import com.globe.rolly.network.model.response_model.GetPostListResponseModel
import com.globe.rolly.network.model.response_model.LoadCommentListResponseModel
import com.globe.rolly.network.model.response_model.MyPageHomeInfoResponseModel
import com.globe.rolly.network.model.response_model.NationCodeResponseModel
import com.globe.rolly.network.model.spot.geocode.GeocodeByGpsRequestModel
import com.globe.rolly.network.model.spot.geocode.GeocodeByGpsResponseModel
import com.globe.rolly.network.model.spot.geocode.GeocodeByPlaceIdRequestModel
import com.globe.rolly.network.model.spot.geocode.GeocodeByPlaceIdResponseModel
import com.globe.rolly.network.model.user.signin.SignInModel
import com.globe.rolly.network.model.user.signin.SignInRequestModel
import com.globe.rolly.network.model.user.signup.SignUpRequestModel
import com.globe.rolly.network.model.user.signup.SignUpResponseModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Response
import okhttp3.ResponseBody

class RollyGlobeApiClient(private val rollyGlobeApiInterface: RollyGlobeApiInterface) {

    fun getNationCodeInfoList(): Observable<List<NationCodeResponseModel>> {
        return rollyGlobeApiInterface.getNationCodeInfoList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun signUp(param: SignUpRequestModel): Observable<SignUpResponseModel> {
        return rollyGlobeApiInterface.signUp(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun signIn(param: SignInRequestModel): Observable<SignInModel> {
        return rollyGlobeApiInterface.signIn(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getGeocodeByGps(param: GeocodeByGpsRequestModel): Observable<GeocodeByGpsResponseModel> {
        return rollyGlobeApiInterface.getGeocodeByGps(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getGeocodeByPlaceId(param: GeocodeByPlaceIdRequestModel): Observable<GeocodeByPlaceIdResponseModel> {
        return rollyGlobeApiInterface.getGeocodeByPlaceId(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getRecommendList(param: RecommendRequestModel): Observable<RecommendListResponseModel> {
        return rollyGlobeApiInterface.getRecommendList(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getSpotInnerContents(param: InnerContentsRequestModel): Observable<SpotInnerContentsResponseModel> {
        return rollyGlobeApiInterface.getSpotInnerContents(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMyPageHomeInfo(param: MyPageHomeRequestModel): Observable<MyPageHomeInfoResponseModel> {
        return rollyGlobeApiInterface.getMyPageHomeInfo(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun editUserName(param: EditUserNameRequestModel): Observable<EditUserNameResponseModel> {
        return rollyGlobeApiInterface.editUserName(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun editUserPhoneNumber(param: EditUserPhoneNumberRequestModel): Observable<EditUserPhoneNumberResponseModel> {
        return rollyGlobeApiInterface.editUserPhoneNumber(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun editUserEmail(param: EditUserEmailRequestModel): Observable<EditUserEmailResponseModel> {
        return rollyGlobeApiInterface.editUserEmail(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun editUserGender(param: EditUserGenderRequestModel): Observable<EditUserGenderResponseModel> {
        return rollyGlobeApiInterface.editUserGender(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun editUserBirthday(param: EditUserBirthdayRequestModel): Observable<EditUserBirthdayResponseModel> {
        return rollyGlobeApiInterface.editUserBirthday(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun editUserPassword(param: EditUserPasswordRequestModel): Observable<EditUserPasswordResponseModel> {
        return rollyGlobeApiInterface.editUserPassword(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadCommentList(param: LoadCommentListRequestModel): Observable<List<LoadCommentListResponseModel>> {
        return rollyGlobeApiInterface.loadCommentList(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getPostList(param: GetPostListRequestModel): Observable<GetPostListResponseModel> {
        return rollyGlobeApiInterface.getPostList(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}