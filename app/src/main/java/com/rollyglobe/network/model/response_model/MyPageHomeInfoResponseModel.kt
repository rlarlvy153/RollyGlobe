package com.rollyglobe.network.model.response_model

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

class MyPageHomeInfoUserModel (
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

    @SerializedName("user_nation_num")
    var userNationCode:String,

    @SerializedName("user_nation_set_num")
    var userNationSetNum:String,

    @SerializedName("user_sex")
    var userSex : String,

    @SerializedName("msg")
    var errorMsg:String,

    @SerializedName("reservationJSON")
    var reservationInfoList : String
)
class MyPageHomeInfoResponseModel(
    @SerializedName("user")
    var user : MyPageHomeInfoUserModel
)