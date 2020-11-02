package com.globe.rolly.network.model.request_model

import com.google.gson.annotations.SerializedName

class LoadCommentListOption(
    @SerializedName("post_num")
    val postNum:Int,

    @SerializedName("post_type")
    val postType:String
)

class LoadCommentListRequest(
    @SerializedName("option")
    val option:LoadCommentListOption
) {
    @SerializedName("funcName")
    val funcName:String = "LoadCommentList"
}
class LoadCommentListRequestModel(
    @SerializedName("request")
    val request : LoadCommentListRequest
)