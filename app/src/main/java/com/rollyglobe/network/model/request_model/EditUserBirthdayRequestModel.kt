package com.rollyglobe.network.model.request_model

import com.google.gson.annotations.SerializedName

class EditUserBirthdayOption(
    @SerializedName("type")
    var type : String,

    @SerializedName("birth_year")
    var year : String,

    @SerializedName("birth_month")
    var month : String,

    @SerializedName("birth_day")
    var day : String
)

class EditUserBirthdayRequest(
    @SerializedName("funcName")
    var funcName:String = "",

    @SerializedName("option")
    var option:EditUserBirthdayOption
)
class EditUserBirthdayRequestModel{
    @SerializedName("request")
    var request :EditUserBirthdayRequest

    constructor(year:String, month:String, day:String){
        val option = EditUserBirthdayOption("birthday", year,month,day)
        request = EditUserBirthdayRequest("EditUserInfo", option)

    }
}