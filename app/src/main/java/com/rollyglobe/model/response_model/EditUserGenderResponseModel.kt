package com.rollyglobe.model.response_model

import com.google.gson.annotations.SerializedName

class EditUserGenderResponseModel (
    @SerializedName("msg")
    val msg:String,

    @SerializedName("sex")
    val gender:String,

    @SerializedName("success")
    val success:Boolean

)