package com.globe.rolly.network.model.edit_user_info.gender

import com.google.gson.annotations.SerializedName

class EditUserGenderResponseModel (
    @SerializedName("msg")
    val msg:String,

    @SerializedName("sex")
    val gender:String,

    @SerializedName("success")
    val success:Boolean

)