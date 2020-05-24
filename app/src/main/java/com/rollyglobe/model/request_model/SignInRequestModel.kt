package com.rollyglobe.model.request_model

import com.google.gson.annotations.SerializedName

class SignInOption(
    @SerializedName("type")
    var type :String,

    @SerializedName("email_address")
    var email_address :String ,

    @SerializedName("email_password")
    var email_password :String,

    @SerializedName("auto_login")
    var auto_login:Boolean
)
class SignInRequest(
    @SerializedName("funcName")
    var funcName:String = "",

    @SerializedName("option")
    var option: SignInOption
)
class SignInRequestModel (

    @SerializedName("request")
    var request: SignInRequest
)