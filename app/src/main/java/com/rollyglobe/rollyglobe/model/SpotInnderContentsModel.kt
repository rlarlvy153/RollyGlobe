package com.rollyglobe.rollyglobe.model

import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber

data class SpotProduct(
    val productNum : Int,
    val productName : String,
    val productThumbnailNum : Int,
    val productThumbType : String,
    val productCost : String,
    val productIntro : String

)

class SpotInnderContentsModel(resultJson: JSONObject,defaultString:String) {
    val gson : Gson = Gson()
    var spotNum : Int
    var spotTitleKor : String
    var spotTitleEng : String
    var spotTitleNative : String
    var spotContinent : String
    var spotNation : String
    var spotCity : String
    var spotIntro : String
    var spotDetail : String
    var spotTime : String
    var spotCost : String
    var spotAddress : String
    var spotTraffic : String
    var spotContact : String
    var spotWeb : String
    var spotLat : Double
    var spotLong : Double
    var spotTagCount : Int
    var spotMajorTagNumber : Int
    var spotMajorTag : String

    var tagList = ArrayList<String>()
    var spotProductJSONArrayList = ArrayList<SpotProduct>()


    init{
        spotNum = resultJson.getInt("spot_num")
        spotTitleKor = resultJson.getString("spot_title_kor")
        spotTitleEng = resultJson.getString("spot_title_eng")
        spotTitleNative = resultJson.getString("spot_title_native")
        spotContinent = resultJson.getString("city_list${resultJson.getInt("spot_continent")}")
        spotNation = resultJson.getString("city_list${resultJson.getInt("spot_nation")}")
        spotCity = resultJson.getString("city_list${resultJson.getInt("spot_city")}")
        spotIntro = resultJson.getString("spot_intro")
        spotDetail = resultJson.getString("spot_detail").let{
            if(it.isBlank())
                defaultString
            else it
        }
        spotTime = resultJson.getString("spot_time").let{
            if(it.isBlank())
                defaultString
            else it
        }
        spotCost = resultJson.getString("spot_cost").let{
            if(it.isBlank())
                defaultString
            else it
        }
        spotAddress = resultJson.getString("spot_address").let{
            if(it.isBlank())
                defaultString
            else it
        }
        spotTraffic = resultJson.getString("spot_traffic").let{
            if(it.isBlank())
                defaultString
            else it
        }
        spotContact = resultJson.getString("spot_contact").let{
            if(it.isBlank())
                defaultString
            else it
        }
        spotWeb = resultJson.getString("spot_web").let{
            if(it.isBlank())
                defaultString
            else it
        }
        spotLat = resultJson.getDouble("spot_lat")
        spotLong = resultJson.getDouble("spot_long")
        spotTagCount = resultJson.getInt("spot_tag_cnt")
        spotMajorTagNumber = resultJson.getInt("spot_major_tag")
        spotMajorTag = resultJson.getString("spot_tag_name$spotMajorTagNumber")
        Timber.d(spotMajorTag)

        for(i in 0 until spotTagCount){
            val tag = resultJson.getString("spot_tag_name${resultJson.getInt("spot_tag$i")}")
            tagList.add(tag)
            Timber.d(tag)
        }

        val spotProductJSONArray = JSONArray(resultJson.getString("spotProductJSON"))
//        Timber.d(spotProductJSONArray.toString())
        for( i in 0 until spotProductJSONArray.length()){
            val each  = spotProductJSONArray.getJSONObject(i)
            val obj = SpotProduct(each.getInt("productNum"), each.getString("productName"),
                                each.getInt("productThumbnailNum"), each.getString("productThumbnailType"),
                                each.getString("productCost"), each.getString("productIntro"))
            spotProductJSONArrayList.add(obj)

        }


    }
}