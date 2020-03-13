package com.rollyglobe.rollyglobe.Model

import com.google.gson.JsonObject
import org.json.JSONObject

class SpotInnderContentsModel(resultJson: JSONObject) {
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

    init{
        spotNum = resultJson.getInt("spot_num")
        spotTitleKor = resultJson.getString("spot_title_kor")
        spotTitleEng = resultJson.getString("spot_title_eng")
        spotTitleNative = resultJson.getString("spot_title_native")
        spotContinent = resultJson.getString("city_list${resultJson.getInt("spot_continent")}")
        spotNation = resultJson.getString("city_list${resultJson.getInt("spot_nation")}")
        spotCity = resultJson.getString("city_list${resultJson.getInt("spot_city")}")
        spotIntro = resultJson.getString("spot_intro")
        spotDetail = resultJson.getString("spot_detail")
        spotTime = resultJson.getString("spot_time")
        spotCost = resultJson.getString("spot_cost")
        spotAddress = resultJson.getString("spot_address")
        spotTraffic = resultJson.getString("spot_traffic")
        spotContact = resultJson.getString("spot_contact")
        spotWeb = resultJson.getString("spot_web")
        spotLat = resultJson.getDouble("spot_lat")
        spotLong = resultJson.getDouble("spot_long")
    }
}