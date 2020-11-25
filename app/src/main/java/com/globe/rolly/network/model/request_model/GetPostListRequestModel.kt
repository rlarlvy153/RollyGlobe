package com.globe.rolly.network.model.request_model

import com.google.gson.annotations.SerializedName

class GetPostListOption(
    @SerializedName("row_num")
    val rowNum : Int,

    @SerializedName("page_num")
    val pageNum : Int
)
class GetPostListRequest(
    @SerializedName("funcName")
    var funcName:String = "GetPostList",

    @SerializedName("option")
    var option:GetPostListOption
)
class GetPostListRequestModel(
    @SerializedName("request")
    var request:GetPostListRequest
)