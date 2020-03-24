package com.rollyglobe.rollyglobe.Model.response_model

import com.google.gson.annotations.SerializedName

class NationCodeResponseModel{
    @SerializedName("nationNameKor")
    val nationNameKor:String = ""

    @SerializedName("nationNum")
    val nationNum:String = ""

    @SerializedName("nationAlpha3")
    val nationAlpha3:String =""

    @SerializedName("nationAlpha2")
    val nationAlpha2:String = ""

    @SerializedName("localPhoneNum")
    val localPhoneNum:String = ""

    @SerializedName("localPhoneNumOrigin")
    val localPhoneNumOrigin:String = ""

    override fun toString():String ="+$nationNum($nationNameKor)"

}