package com.globe.rolly.network.model.request_model

import com.google.gson.annotations.SerializedName

class RecommendOption (

    @SerializedName("tagRemoteButton")
    var tagRemoteButton : ArrayList<Int> ,

    @SerializedName("continentRemoteButton")
    var continentRemoteButton :String ,

    @SerializedName("nationRemoteButton")
    var nationRemoteButton : String,

    @SerializedName("locationRemoteButton")
    var locationRemoteButton: String,

    @SerializedName("searchQueryArray")
    var searchQueryArray : String,

    @SerializedName("recentArea")
    var recentArea : String
)
class RecommendRequest(
    @SerializedName("funcName")
    var funcName:String = "",

    @SerializedName("option")
    var option:RecommendOption
)
class RecommendRequestModel(
    @SerializedName("request")
    var request:RecommendRequest
)