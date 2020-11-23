package com.globe.rolly.network.model.response_model

import com.google.gson.annotations.SerializedName

class PostInfo(
    @SerializedName("post_num")
    val postNum: Int
)

class PostInfoResult(
    @SerializedName("post_list")
    val postInfo: List<PostInfo>,

    @SerializedName("post_list_total_cnt")
    val postListTotalCnt: Int
)

class GetPostListResponseModel(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("msg")
    val msg: String,

    @SerializedName("result")
    val postList: PostInfoResult
)