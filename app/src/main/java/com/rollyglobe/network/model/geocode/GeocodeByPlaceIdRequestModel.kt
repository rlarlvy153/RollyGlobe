package com.rollyglobe.network.model.geocode

import com.google.gson.annotations.SerializedName

class PlaceIdOption(

    @SerializedName("place_id")
    var placeId :String

)

class GeocodeByPlaceIdRequest(
    @SerializedName("funcName")
    var funcName:String = "",

    @SerializedName("option")
    var option: PlaceIdOption
)
class GeocodeByPlaceIdRequestModel{
    @SerializedName("request")
    lateinit var request: GeocodeByPlaceIdRequest
}