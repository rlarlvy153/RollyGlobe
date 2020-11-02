package com.globe.rolly.network.model.spot.geocode

import com.google.gson.annotations.SerializedName

class GeocodeByGpsResponseModel (
    @SerializedName("msg")
    val msg:String,

    @SerializedName("success")
    val success:Boolean,

    @SerializedName("geocode")
    val geocode:String
)