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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

class RollyGlobeApiClient(private val rollyGlobeApiInterface: RollyGlobeApiInterface) {

    fun getNationCodeInfoList(): Single<List<NationCodeResponseModel>> {
        return rollyGlobeApiInterface.getNationCodeInfoList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun signUp(param: SignUpRequestModel): Single<SignUpResponseModel> {
        return rollyGlobeApiInterface.signUp(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun signIn(param: SignInRequestModel): Single<SignInModel> {
        return rollyGlobeApiInterface.signIn(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getGeocodeByGps(param: GeocodeByGpsRequestModel): Observable<GeocodeByGpsResponseModel> {
        return rollyGlobeApiInterface.getGeocodeByGps(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getGeocodeByPlaceId(param:GeocodeByPlaceIdRequestModel):Observable<GeocodeByPlaceIdResponseModel>{
        return rollyGlobeApiInterface.getGeocodeByPlaceId(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getRecommendList(param: RecommendRequestModel): Single<RecommendListResponseModel> {
        return rollyGlobeApiInterface.getRecommendList(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getSpotInnerContents(param: InnerContentsRequestModel): Single<ResponseBody> {
        return rollyGlobeApiInterface.getSpotInnerContents(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMyPageHomeInfo(param: MyPageHomeRequestModel): Single<MyPageHomeInfoResponseModel> {
        return rollyGlobeApiInterface.getMyPageHomeInfo(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun editUserName(param: EditUserNameRequestModel): Single<EditUserNameResponseModel> {
        return rollyGlobeApiInterface.editUserName(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun editUserPhoneNumber(param: EditUserPhoneNumberRequestModel): Single<EditUserPhoneNumberResponseModel> {
        return rollyGlobeApiInterface.editUserPhoneNumber(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun editUserEmail(param: EditUserEmailRequestModel): Single<EditUserEmailResponseModel> {
        return rollyGlobeApiInterface.editUserEmail(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun editUserGender(param: EditUserGenderRequestModel): Single<EditUserGenderResponseModel> {
        return rollyGlobeApiInterface.editUserGender(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun editUserBirthday(param: EditUserBirthdayRequestModel): Single<EditUserBirthdayResponseModel> {
        return rollyGlobeApiInterface.editUserBirthday(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun editUserPassword(param: EditUserPasswordRequestModel): Single<EditUserPasswordResponseModel> {
        return rollyGlobeApiInterface.editUserPassword(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadCommentList(param: LoadCommentListRequestModel): Single<List<LoadCommentListResponseModel>> {
        return rollyGlobeApiInterface.loadCommentList(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}