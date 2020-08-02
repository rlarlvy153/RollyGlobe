package com.rollyglobe.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SpotModel (
    @SerializedName("spot_num")
    val spotNum : Int,

    @SerializedName("spot_title_kor")
    val spotTitleKor:String,

    @SerializedName("spot_continent")
    val spotContinent:Int,

    @SerializedName("spot_nation")
    val spotNation : Int,

    @SerializedName("spot_city")
    val spotCity:Int,

    @SerializedName("spot_intro")
    val spotIntro : String,

    @SerializedName("spot_thumbnail")
    var spotThumbnailPath:String,

    @SerializedName("spot_regdate")
    var spotRegDate:String,

    @SerializedName("user_nickname")
    var firstUploadUser:String,

    @SerializedName("spot_recommenation_reason")
    var recommendationReason : String
) : Serializable{
    val spotThumbnailHeader = "https://rollyglobe.com/_post/pics/resizedBig/"

}