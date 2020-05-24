package com.rollyglobe.network.model.request_model

import com.google.gson.annotations.SerializedName

class EditUserEmailOption(
    @SerializedName("type")
    var type : String,

    @SerializedName("email")
    var name : String


)

class EditUserEmailRequest(
    @SerializedName("funcName")
    var funcName:String = "",

    @SerializedName("option")
    var option:EditUserEmailOption
)
class EditUserEmailRequestModel{
    @SerializedName("request")
    var request :EditUserEmailRequest

    constructor(email:String){
        val option = EditUserEmailOption("email", email)
        request = EditUserEmailRequest("EditUserInfo", option)

    }
}