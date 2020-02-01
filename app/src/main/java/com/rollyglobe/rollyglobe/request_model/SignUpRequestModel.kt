package com.rollyglobe.rollyglobe.request_model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.http.FormUrlEncoded
class Option(

    @SerializedName("email_address")
    var emailAddress :String,

    @SerializedName("nickname_input")
    var nickName :String = "",

    @SerializedName("pw_input")
    var pw :String = "",

    @SerializedName("nation_num")
    var nationNum:String  = "",

    @SerializedName("phone_number")
    var phoneNumber:String  = "",

    @SerializedName("user_sex")
    var sex:String  = "",

    @SerializedName("sign_up_birth_year")
    var year:String  = "",

    @SerializedName("sign_up_birth_month")
    var month:String ="",

    @SerializedName("sign_up_birth_day")
    var day:String  =""

)

class Request(
    @SerializedName("funcName")
    var funcName:String = "",

    @SerializedName("option")
    var option:Option
)
class SignUpRequestModel{
    @SerializedName("request")
    lateinit var request:Request
}