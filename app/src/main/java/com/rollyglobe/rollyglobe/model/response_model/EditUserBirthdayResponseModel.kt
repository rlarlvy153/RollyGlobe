package com.rollyglobe.rollyglobe.model.response_model

import com.google.gson.annotations.SerializedName

class EditUserBirthdayResponseModel (
    @SerializedName("msg")
    val msg:String,

    @SerializedName("birthday")
    val birthday:String,

    @SerializedName("success")
    val success:Boolean

)