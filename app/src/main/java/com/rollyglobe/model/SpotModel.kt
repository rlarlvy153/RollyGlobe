package com.rollyglobe.model

import java.io.Serializable

class SpotModel (
    val spotNum : Int,
    val spotThumbnailNum : Int,
    val spotThumbnailType : String,
    val spotTitleKor:String,
    val spotContinentName:String,
    val spotNationName : String,
    val spotCityName:String,
    val spotIntro : String,
    val spotRecommendationReason:String
) : Serializable{
    var spotThumbnailPath : String
    val spotThumbnailHeader = "https://rollyglobe.com/_post/pics/resizedBig/"
    init{
        spotThumbnailPath = "$spotThumbnailHeader${spotThumbnailNum}.${spotThumbnailType}"
    }

}