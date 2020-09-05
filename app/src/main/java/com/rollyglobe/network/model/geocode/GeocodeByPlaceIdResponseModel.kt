package com.rollyglobe.network.model.geocode

import com.google.gson.annotations.SerializedName

class GeocodeByPlaceIdResponseModel (
    @SerializedName("msg")
    val msg:String,

    @SerializedName("success")
    val success:Boolean,

    @SerializedName("geocode")
    val geocode:String
)