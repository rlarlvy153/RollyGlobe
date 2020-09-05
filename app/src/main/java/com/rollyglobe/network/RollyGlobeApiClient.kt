package com.rollyglobe.network

import com.rollyglobe.network.model.RecommendListResponseModel
import com.rollyglobe.network.model.request_model.*
import com.rollyglobe.network.model.response_model.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

class RollyGlobeApiClient (private val apiInterface:RollyGlobeApiInterface){

    fun getNationCodeInfoList(): Single<List<NationCodeResponseModel>>{
        return apiInterface.getNationCodeInfoList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun SignUp(param : SignUpRequestModel): Single<SignUpResponseModel>{
        return apiInterface.SignUp(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun SignIn(param: SignInRequestModel): Single<SignInModel>{
        return apiInterface.SignIn(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getRecommendList(param: RecommendRequestModel): Single<RecommendListResponseModel>{
        return apiInterface.getRecommendList(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getSpotInnerContents(param: InnerContentsRequestModel): Single<ResponseBody>{
        return apiInterface.getSpotInnerContents(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMyPageHomeInfo(param: MyPageHomeRequestModel): Single<MyPageHomeInfoResponseModel>{
        return apiInterface.getMyPageHomeInfo(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun EditUserName(param: EditUserNameRequestModel) : Single<EditUserNameResponseModel>{
        return apiInterface.EditUserName(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun EditUserPhoneNumber(param: EditUserPhoneNumberRequestModel) : Single<EditUserPhoneNumberResponseModel>{
        return apiInterface.EditUserPhoneNumber(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun EditUserEmail(param: EditUserEmailRequestModel) : Single<EditUserEmailResponseModel>{
        return apiInterface.EditUserEmail(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun EditUserGender(param: EditUserGenderRequestModel) : Single<EditUserGenderResponseModel>{
        return apiInterface.EditUserGender(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun EditUserBirthday(param: EditUserBirthdayRequestModel) : Single<EditUserBirthdayResponseModel>{
        return apiInterface.EditUserBirthday(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun EditUserPassword(param: EditUserPasswordRequestModel) : Single<EditUserPasswordResponseModel>{
        return apiInterface.EditUserPassword(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun LoadCommentList(param: LoadCommentListRequestModel) : Single<List<LoadCommentListResponseModel>>{
        return apiInterface.LoadCommentList(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}