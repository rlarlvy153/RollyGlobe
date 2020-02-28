package com.rollyglobe.rollyglobe.response_model

import com.google.gson.annotations.SerializedName

class UserInfo(
    @SerializedName("user_num")
    val user_num :Int,

    @SerializedName("user_email")
    val user_email : String,

    @SerializedName("user_fb")
    val user_fb : String,

    @SerializedName("user_nickname")
    val user_nickname : String,

    @SerializedName("user_nation_num")
    val user_nation_num : Int,

    @SerializedName("user_nation_set_num")
    val user_nation_set_num : Int,

    @SerializedName("user_phone_num")
    val user_phone_num : String,

    @SerializedName("user_birthday")
    val user_birthday : Long,

    @SerializedName("user_sex")
    val user_sex : String ,

    @SerializedName("user_level")
    val user_level : String,

    @SerializedName("user_signup_date")
    val user_signup_date : Long,

    @SerializedName("user_login_date")
    val user_login_date : Long,

    @SerializedName("user_signup_ip")
    val user_signup_ip : String,

    @SerializedName("user_login_ip")
    val user_login_ip : String,

    @SerializedName("user_drop_out")
    val user_drop_out : Int
)

class SignInModel {
    @SerializedName("msg")
    val msg:String = ""

    @SerializedName("success")
    val success:Boolean = false

    @SerializedName("user_info")
    lateinit var user_info : UserInfo
}