package com.rollyglobe.network.model.edit_user_info.password

import com.google.gson.annotations.SerializedName

class EditUserPasswordOption(
    @SerializedName("type")
    var type : String,

    @SerializedName("current_password")
    var currentPassword : String,

    @SerializedName("new_password")
    var newPassword : String

)

class EditUserPasswordRequest(
    @SerializedName("funcName")
    var funcName:String = "",

    @SerializedName("option")
    var option: EditUserPasswordOption
)
class EditUserPasswordRequestModel{
    @SerializedName("request")
    var request : EditUserPasswordRequest

    constructor(oldPassword:String, newPassword:String){
        val option = EditUserPasswordOption("password", oldPassword, newPassword)
        request = EditUserPasswordRequest("EditUserInfo", option)

    }
}