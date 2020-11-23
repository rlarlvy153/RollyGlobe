package com.globe.rolly.network.model.response_model

import com.google.gson.annotations.SerializedName

class PostInfoPicture(
    @SerializedName("post_pic_name")
    val postPicName: String,


    @SerializedName("post_pic_regdate")
    val pictureRegDate: String
)

class PostInfo(
    @SerializedName("post_num")
    val postNum: Int,

    @SerializedName("user_nickname")
    val userNickName: String,

    @SerializedName("post_regdate")
    val postRegdate: String,

    @SerializedName("post_content")
    val postContent: String,

    @SerializedName("post_pic_info")
    val postPicInfo: List<PostInfoPicture>


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