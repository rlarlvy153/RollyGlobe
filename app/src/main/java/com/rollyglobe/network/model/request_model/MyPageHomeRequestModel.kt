package com.rollyglobe.network.model.request_model

import com.google.gson.annotations.SerializedName


class MyPageHomeRequest(
    @SerializedName("funcName")
    var funcName:String = "",

    @SerializedName("option")
    var option:String =""
)
class MyPageHomeRequestModel (
    @SerializedName("request")
    var request: MyPageHomeRequest
)