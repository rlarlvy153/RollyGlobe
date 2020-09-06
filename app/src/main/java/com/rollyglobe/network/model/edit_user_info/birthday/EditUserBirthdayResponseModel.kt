package com.rollyglobe.network.model.edit_user_info.birthday

import com.google.gson.annotations.SerializedName

class EditUserBirthdayResponseModel (
    @SerializedName("msg")
    val msg:String,

    @SerializedName("birthday")
    val birthday:String,

    @SerializedName("success")
    val success:Boolean

)