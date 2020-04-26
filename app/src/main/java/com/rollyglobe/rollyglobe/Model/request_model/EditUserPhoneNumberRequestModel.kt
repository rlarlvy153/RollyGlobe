package com.rollyglobe.rollyglobe.Model.request_model

import com.google.gson.annotations.SerializedName

class EditUserPhoneNumberOption(
    @SerializedName("type")
    var type : String,

    @SerializedName("phone_num")
    var phoneNumber : String,

    @SerializedName("nation_num")
    var nationCode : Int

)

class EditUserPhoneNumbeRequest(
    @SerializedName("funcName")
    var funcName:String = "",

    @SerializedName("option")
    var option:EditUserPhoneNumberOption
)
class EditUserPhoneNumberRequestModel{
    @SerializedName("request")
    var request :EditUserPhoneNumbeRequest

    constructor(phoneNumber:String, nationCode:Int){
        val option = EditUserPhoneNumberOption("phone_num", phoneNumber,nationCode)
        request = EditUserPhoneNumbeRequest("EditUserInfo", option)

    }
}