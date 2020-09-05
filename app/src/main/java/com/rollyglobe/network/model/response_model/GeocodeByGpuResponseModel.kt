package com.rollyglobe.network.model.response_model

import com.google.gson.annotations.SerializedName

class GeocodeByGpuResponseModel (
    @SerializedName("msg")
    val msg:String,

    @SerializedName("success")
    val success:Boolean,

    @SerializedName("geocode")
    val geocode:String
)