package com.rollyglobe.rollyglobe.Model.response_model

import com.google.gson.annotations.SerializedName

class EditUserNameResponseModel (
    @SerializedName("msg")
    val msg:String,

    @SerializedName("success")
    val success:Boolean

)