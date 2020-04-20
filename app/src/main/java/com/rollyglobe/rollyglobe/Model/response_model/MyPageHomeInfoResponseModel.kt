package com.rollyglobe.rollyglobe.Model.response_model

import android.provider.ContactsContract
import com.google.gson.annotations.SerializedName


class ReservationModel(
    @SerializedName("reservationNum")
    var reservationNum:Int,

    @SerializedName("reservationProductNum")
    var reservationProductNum : Int,

    @SerializedName("reservationName")
    var reservationName:String,

    @SerializedName("reservationIntro")
    var reservationIntro:String,

    @SerializedName("reservationThumbnailNum")
    var reservationThumbnailNum:Int,

    @SerializedName("reservationThumbnailType")
    var reservationThumbnailType:String
)

class MyPageHomeInfoResponseModel (
    @SerializedName("success")
    var success : Boolean,

    @SerializedName("user_email")
    var userEmail : String,

    @SerializedName("user_nickname")
    var userNickname: String,

    @SerializedName("user_phone_num")
    var userPhoneNum : String,

    @SerializedName("user_birthday")
    var userBirthday:String,

    @SerializedName("user_sex")
    var userSex : String,

    @SerializedName("msg")
    var errorMsg:String,

    @SerializedName("reservationJSON")
    var reservationInfoList : String
)