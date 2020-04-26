package com.rollyglobe.rollyglobe.Model.response_model

import com.google.gson.annotations.SerializedName

class EditUserEmailResponseModel (
    @SerializedName("msg")
    val msg:String,

    @SerializedName("email")
    val email:String,

    @SerializedName("success")
    val success:Boolean

)