package com.rollyglobe.network.model.edit_user_info.password

import com.google.gson.annotations.SerializedName

class EditUserPasswordResponseModel (
    @SerializedName("msg")
    val msg:String,

    @SerializedName("success")
    val success:Boolean
)