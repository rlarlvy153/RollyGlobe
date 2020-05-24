package com.rollyglobe.model.response_model

import com.google.gson.annotations.SerializedName

class EditUserPhoneNumberResponseModel (
    @SerializedName("msg")
    val msg:String,

//    @SerializedName("phone_num")
//    val phoneNumber : String,

    @SerializedName("success")
    val success:Boolean

)