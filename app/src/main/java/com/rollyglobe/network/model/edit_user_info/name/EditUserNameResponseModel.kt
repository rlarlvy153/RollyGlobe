package com.rollyglobe.network.model.edit_user_info.name

import com.google.gson.annotations.SerializedName

class EditUserNameResponseModel (
    @SerializedName("msg")
    val msg:String,

    @SerializedName("success")
    val success:Boolean

)