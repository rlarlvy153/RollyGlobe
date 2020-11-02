package com.globe.rolly.network.model.spot.geocode

import com.google.gson.annotations.SerializedName

class GpsOption(

    @SerializedName("lat")
    var lat :Float,

    @SerializedName("lng")
    var lng :Float

)

class GeocodeByGpsRequest(
    @SerializedName("funcName")
    var funcName:String = "",

    @SerializedName("option")
    var option: GpsOption
)
class GeocodeByGpsRequestModel{
    @SerializedName("request")
    lateinit var request: GeocodeByGpsRequest
}