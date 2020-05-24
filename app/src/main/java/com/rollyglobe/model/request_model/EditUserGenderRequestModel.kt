package com.rollyglobe.model.request_model

import com.google.gson.annotations.SerializedName

class EditUserGenderOption(
    @SerializedName("type")
    var type : String,

    @SerializedName("sex")
    var gender : String


)

class EditUserGendereRequest(
    @SerializedName("funcName")
    var funcName:String = "",

    @SerializedName("option")
    var option:EditUserGenderOption
)
class EditUserGenderRequestModel{
    @SerializedName("request")
    var request :EditUserGendereRequest

    constructor(gender:String){
        val option = EditUserGenderOption("sex", gender)
        request = EditUserGendereRequest("EditUserInfo", option)

    }
}