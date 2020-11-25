package com.globe.rolly.network.model.response_model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import timber.log.Timber

class SpotInfo(
    @SerializedName("spot_continent")
    val spotContinent: Int,

    @SerializedName("spot_nation")
    val spotNation: Int,

    @SerializedName("spot_city")
    val spotCity: Int,

    @SerializedName("city_list23")
    val cityList: String


)

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
    val postPicInfo: List<PostInfoPicture>,

    @SerializedName("spot_info")
    val spotInfo: JsonObject

) {
    var continent = ""
    var nation = ""
    var city = ""

    var spotTitleKor = ""
    var spotIntro = ""
    fun parseJson() {
        val continentKey = "city_list${spotInfo["spot_continent"].asInt}"
        val nationKey = "city_list${spotInfo["spot_nation"].asInt}"
        val cityKey = "city_list${spotInfo["spot_city"].asInt}"

        continent = spotInfo[continentKey].asString
        nation = spotInfo[nationKey].asString
        city = spotInfo[cityKey].asString

        spotTitleKor = spotInfo["spot_title_kor"].asString
        spotIntro = spotInfo["spot_intro"].asString

    }
}

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