package com.rollyglobe.rollyglobe.model.request_model

import com.google.gson.annotations.SerializedName

class EditUserNameOption(
    @SerializedName("type")
    var type : String,

    @SerializedName("name")
    var name : String


)

class EditUserNameRequest(
    @SerializedName("funcName")
    var funcName:String = "",

    @SerializedName("option")
    var option:EditUserNameOption
)
class EditUserNameRequestModel{
    @SerializedName("request")
    var request :EditUserNameRequest

    constructor(name:String){
        val option = EditUserNameOption("name", name)
        request = EditUserNameRequest("EditUserInfo", option)

    }
}