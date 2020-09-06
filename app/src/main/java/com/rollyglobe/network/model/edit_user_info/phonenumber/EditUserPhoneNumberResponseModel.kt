package com.rollyglobe.network.model.edit_user_info.phonenumber

import com.google.gson.annotations.SerializedName

class EditUserPhoneNumberResponseModel (
    @SerializedName("msg")
    val msg:String,

//    @SerializedName("phone_num")
//    val phoneNumber : String,

    @SerializedName("success")
    val success:Boolean

)