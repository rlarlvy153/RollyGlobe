package com.globe.rolly.network.model.edit_user_info.email

import com.google.gson.annotations.SerializedName

class EditUserEmailResponseModel (
    @SerializedName("msg")
    val msg:String,

    @SerializedName("email")
    val email:String,

    @SerializedName("success")
    val success:Boolean

)