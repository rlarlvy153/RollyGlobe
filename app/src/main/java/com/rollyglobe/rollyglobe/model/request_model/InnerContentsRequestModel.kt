package com.rollyglobe.rollyglobe.model.request_model

import com.google.gson.annotations.SerializedName

class InnerContentsOption(

    @SerializedName("spotNum")
    var spotNum : Int

)
class InnerContentsRequest(
    @SerializedName("funcName")
    var funcName:String = "",

    @SerializedName("option")
    var option:InnerContentsOption
)
class InnerContentsRequestModel(
    @SerializedName("request")
    var request:InnerContentsRequest
)